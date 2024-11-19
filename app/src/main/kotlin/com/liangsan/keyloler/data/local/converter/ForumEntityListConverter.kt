package com.liangsan.keyloler.data.local.converter

import com.liangsan.keyloler.data.local.entity.ForumEntity

class ForumEntityListConverter : JsonConverter<List<ForumEntity>> by defaultJsonConverter()