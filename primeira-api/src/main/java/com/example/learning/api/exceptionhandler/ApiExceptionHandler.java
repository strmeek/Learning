package com.example.learning.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.learning.domain.exception.EntidadeNaoEncontradaException;
import com.example.learning.domain.exception.NegocioException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{
	
	/* Mensagem personalizada em messages.properties
	private MessageSource messageSource;
	 * */
	/*
	 * Método dá Override em outro dentro de ResponseEntityExceptionHandler
	 * para personalizar a mensagem da API
	 * @param ex, header, status, request
	 * @return ResponseEntity
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		//Lista para mostrar os campos que aconteceram o erro
		List<Problema.Campo> campos = new ArrayList<>();
		
		//percorre todos os lugares que aconteceram o erro
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			String nome = ((FieldError) error).getField();
			
			/*para colocar mensagens personalizadas
			String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale());*/
			
			String mensagem = error.getDefaultMessage();
			//adiciona a lista
			campos.add(new Problema.Campo(nome, mensagem));
			//por algum motivo esse código ja me dá as mensagens tratadas
		}
		
		//Cria mensagem personalizada
		Problema problema = new Problema();
		problema.setStatus(status.value());
		problema.setDataHora(OffsetDateTime.now());
		problema.setTitulo("Um ou mais campos inválidos, tente novamente");
		problema.setCampos(campos);
		
		return handleExceptionInternal(ex, problema , headers, status, request);
	}
	/*
	 * Método lida com todas as exceptions vindas de 
	 * NegocioException
	 * @param ex, request
	 * @return ResponseEntity
	 */
	@ExceptionHandler(NegocioException.class)//Referencia esse método a negocioexception
	public ResponseEntity<Object> handleNegocio(NegocioException ex, WebRequest request){
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		Problema problema = new Problema();
		problema.setStatus(status.value());
		problema.setDataHora(OffsetDateTime.now());
		problema.setTitulo(ex.getMessage());
		
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status , request);
	}
	
	/*
	 * Método lida com as excessões vindas de EntidadeNaoEncontradaException
	 * @param ex, request
	 * @return ResponseEntity
	 */
	@ExceptionHandler(EntidadeNaoEncontradaException.class)//Referencia esse método a negocioexception
	public ResponseEntity<Object> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex, WebRequest request){
		HttpStatus status = HttpStatus.NOT_FOUND;
		
		Problema problema = new Problema();
		problema.setStatus(status.value());
		problema.setDataHora(OffsetDateTime.now());
		problema.setTitulo(ex.getMessage());
		
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status , request);
	}
	
}
