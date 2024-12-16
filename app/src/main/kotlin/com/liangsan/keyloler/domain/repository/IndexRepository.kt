package com.liangsan.keyloler.domain.repository

import com.liangsan.keyloler.domain.model.Index

interface IndexRepository {

    suspend fun getIndexContent(): Index
}