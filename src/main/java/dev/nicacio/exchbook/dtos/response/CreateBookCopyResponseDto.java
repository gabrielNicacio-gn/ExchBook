package dev.nicacio.exchbook.dtos.response;

import dev.nicacio.exchbook.enums.Condition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class CreateBookCopyResponseDto{
    private int idCopy;
    private Condition condition;
    private String title;
    private final List<String> errors = new ArrayList<>();

    public void addErros(String error){
        errors.add(error);
    }
}
