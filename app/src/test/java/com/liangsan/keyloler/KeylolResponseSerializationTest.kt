package com.liangsan.keyloler

import com.liangsan.keyloler.data.remote.dto.ForumIndexDto
import com.liangsan.keyloler.data.remote.dto.KeylolResponse
import kotlinx.serialization.json.Json
import org.junit.Test

class KeylolResponseSerializationTest {
    @Test
    fun addition_isCorrect() {
        val jsonStr = """
        {
          "Version": "4",
          "Charset": "UTF-8",
          "Variables": {
            "catlist": [
              {
                "fid": "121",
                "name": "平台周边",
                "forums": [
                  "161",
                  "127",
                  "257",
                  "235",
                  "129",
                  "319",
                  "234",
                  "271",
                  "254"
                ]
              }
            ],
            "forumlist": [
              {
                "fid": "148",
                "name": "谈天说地",
                "threads": "153425",
                "posts": "4541720",
                "todayposts": "259",
                "description": "水区┊收藏┊理财 ┊健身┊影音",
                "icon": "https://keylol.com/data/attachment/common/../../../template/steamcn_metro/src/img/forum-icons/f148.png",
                "sublist": [
                  {
                    "fid": "340",
                    "name": "互助互利",
                    "threads": "8406",
                    "posts": "81160",
                    "todayposts": "0"
                  }
                ]
              }
            ]
          }
        }
        """.trimIndent()
        val json = Json {
            ignoreUnknownKeys = true
            explicitNulls = false
            prettyPrint = true
        }
        val error = json.decodeFromString<KeylolResponse<ForumIndexDto>>(jsonStr)
        println(error)
    }
}