package com.liangsan.keyloler.data.repository

import com.fleeksoft.ksoup.Ksoup
import com.fleeksoft.ksoup.nodes.Document
import com.liangsan.keyloler.data.remote.KeylolerService
import com.liangsan.keyloler.domain.model.Index
import com.liangsan.keyloler.domain.model.Slide
import com.liangsan.keyloler.domain.model.Thread
import com.liangsan.keyloler.domain.repository.IndexRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class IndexRepositoryImpl(
    private val service: KeylolerService
) : IndexRepository {

    override suspend fun getIndexContent(): Index {
        val html = service.webIndex().getOrElse {
            return Index()
        }

        val document = Ksoup.parseBodyFragment(html)
        val slides = coroutineScope { async { parseSlides(document) } }
        val threads = coroutineScope { async { parseThreadsList(document) } }

        return Index(slides = slides.await(), threadsList = threads.await())
    }

    private fun parseSlides(document: Document): List<Slide> {
        val slideShow = document.getElementsByClass("slideshow").getOrNull(0) ?: return emptyList()

        val elements = slideShow.getElementsByTag("li")
        return elements.mapNotNull { element ->
            val href = element.getElementsByTag("a").getOrNull(0) ?: return@mapNotNull null
            val tid = href.attribute("href")?.value?.substringBefore("-")?.drop(1)
            val img = href.getElementsByTag("img").getOrNull(0)?.attribute("src")?.value
            val title = element.getElementsByClass("title").getOrNull(0)?.text()
            Slide(
                tid = tid ?: return@mapNotNull null,
                img = img ?: return@mapNotNull null,
                title = title ?: return@mapNotNull null
            )
        }
    }

    private fun parseThreadsList(document: Document): Map<String, List<Thread>> {
        val tab = document.getElementById("tabPAhn0P_title")
        return tab?.children()?.associate { children ->
            val id = children.attribute("id")?.value ?: return emptyMap()
            val title =
                children.getElementsByClass("titletext").getOrNull(0)?.text()
                    ?: return emptyMap()
            val list = children.getElementById("${id}_content")?.getElementsByTag("li")?.take(9)
            val threads = list?.mapNotNull { element ->
                element.getElementsByTag("a").let {
                    var author: String? = null
                    var authorId: String? = null
                    var subject: String? = null
                    var tid: String? = null
                    var dateline: String? = null
                    var forum: String? = null
                    it.first()?.let { firstElement ->
                        author = firstElement.text()
                        authorId = firstElement.attribute("href")?.value?.substringAfter("suid-")
                    }
                    it.last()?.let { lastElement ->
                        tid = lastElement.attribute("href")?.value?.substringBefore("-")?.drop(1)
                        subject = lastElement.text()
                        lastElement.attribute("title")?.value?.let {
                            forum = it.substringBefore("\n").substringAfter("板块: ")
                            val regex = "作者.+?\\(([^)]+)\\)".toRegex()
                            dateline = regex.find(it)?.groupValues?.getOrNull(1)
                        }
                    }
                    Thread(
                        tid = tid ?: return@mapNotNull null,
                        author = author ?: return@mapNotNull null,
                        authorId = authorId ?: return@mapNotNull null,
                        subject = subject ?: return@mapNotNull null,
                        dateline = dateline ?: "",
                        forum = forum
                    )
                }
            } ?: emptyList()
            title to threads
        } ?: emptyMap()
    }
}