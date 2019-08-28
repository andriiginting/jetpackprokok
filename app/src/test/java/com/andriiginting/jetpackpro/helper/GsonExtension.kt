package com.andriiginting.jetpackpro.helper

import androidx.paging.PagedList
import com.google.gson.Gson
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import java.io.InputStreamReader

fun <T> load(clazz: Class<T>, file: String): T {
    val fixtureStreamReader = InputStreamReader(clazz.classLoader?.getResourceAsStream(file))
    return Gson().fromJson(fixtureStreamReader, clazz)
}