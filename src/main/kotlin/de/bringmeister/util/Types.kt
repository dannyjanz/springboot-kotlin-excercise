package de.bringmeister.util

import org.springframework.core.ParameterizedTypeReference

inline fun <reified T: Any> typeOf(): ParameterizedTypeReference<T> = object: ParameterizedTypeReference<T>(){}