package br.com.zupacademy.thiago.mercadolivre.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ExceptionHandling {

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErroValidacao> inputInvalido(MethodArgumentNotValidException ex) {

        List<ErroValidacao> erros = new ArrayList<ErroValidacao>();

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        fieldErrors.forEach(fe -> {
            String mensagem = messageSource.getMessage(fe, LocaleContextHolder.getLocale());
            ErroValidacao erroValidacao = new ErroValidacao(fe.getField(), mensagem);
            erros.add(erroValidacao);
        });

        List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();
        globalErrors.forEach(ge -> {
            String mensagem = messageSource.getMessage(ge, LocaleContextHolder.getLocale());
            ErroValidacao erroValidacao = new ErroValidacao(ge.getObjectName(), mensagem);
            erros.add(erroValidacao);
        });

        return erros;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public List<ErroValidacao> inputInvalido(BindException ex) {

        List<ErroValidacao> erros = new ArrayList<ErroValidacao>();

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        fieldErrors.forEach(fe -> {
            String mensagem = messageSource.getMessage(fe, LocaleContextHolder.getLocale());
            ErroValidacao erroValidacao = new ErroValidacao(fe.getField(), mensagem);
            erros.add(erroValidacao);
        });

        //Só para ver retornar mensagem de que se tentou comfirmar um pagamento
        //que já estava confirmado com sucesso.
        if (erros.size() == 0) {
            erros.add(new ErroValidacao("idDoPagamentoNoMeioDePagamento",
                    ex.getBindingResult().getGlobalErrors().get(0).getDefaultMessage()));
        }
        return erros;
    }

}
