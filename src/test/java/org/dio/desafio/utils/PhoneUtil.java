package org.dio.desafio.utils;

import org.dio.desafio.dto.requests.PhoneDTO;
import org.dio.desafio.entities.Phone;
import org.dio.desafio.enums.PhoneType;

public class PhoneUtil {

    private static final Long PHONE_ID = 1L;
    private static final PhoneType PHONE_TYPE = PhoneType.MOBILE;
    private static final String PHONE_NUMBER = "11 984263-6599";

    public static PhoneDTO createDTO() {
        return PhoneDTO.builder()
                .id(PHONE_ID)
                .type(PHONE_TYPE)
                .number(PHONE_NUMBER)
                .build();
    }

    public static Phone createEntity() {
        return Phone.builder()
                .id(PHONE_ID)
                .type(PHONE_TYPE)
                .number(PHONE_NUMBER)
                .build();
    }

}
