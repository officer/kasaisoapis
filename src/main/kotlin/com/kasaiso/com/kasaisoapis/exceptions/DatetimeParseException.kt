package com.kasaiso.com.kasaisoapis.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
class DatetimeParseException(
    m: String
) : Exception(m) {}