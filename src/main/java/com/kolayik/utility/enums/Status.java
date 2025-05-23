package com.kolayik.utility.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum Status {
    AKTIF,
    PASIF,
    ASKIDA
}
