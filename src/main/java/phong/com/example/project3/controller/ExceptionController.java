package phong.com.example.project3.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import phong.com.example.project3.dto.ResponseDTO;

import java.net.BindException;
import java.sql.SQLException;

public class ExceptionController {
    Logger logger = LoggerFactory.getLogger(this.getClass());
//    @ExceptionHandler({ BindException.class })
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
////    public ResponseDTO bind(BindException ex) {
//
//        logger.error("ERROR: ", ex);
//
//        String msg = ex.getFieldError().getField() + " "
//                + ex.getFieldError().getDefaultMessage();
//
//        return new ResponseDTO(400, msg);
//    }

    // bat cac loai exception tai day
    @ExceptionHandler({ Exception.class })
    public String exception(SQLException ex) {
        // ex.printStackTrace();
        logger.error("sql ex: ", ex);
        return "exception.html";// view
    }
}
