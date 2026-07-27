package com.arthur_pereira.mind_cracker_server_api.data;

import java.util.Date;

public record ExceptionResult(String message, Date moment, String details) {

}
