package com.backendoori.ootw;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import com.backendoori.ootw.dto.PostReadResponse;
import com.backendoori.ootw.dto.PostSaveRequest;
import com.backendoori.ootw.dto.PostSaveResponse;
import com.backendoori.ootw.exception.ExceptionResponse;
import com.backendoori.ootw.exception.ExceptionResponse.FieldErrorDetail;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
@RestController
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostSaveResponse> save(@RequestBody @Valid PostSaveRequest request) {

        PostSaveResponse response = postService.save(request);

        return ResponseEntity.created(URI.create("/api/v1/posts/" + response.getPostId()))
            .contentType(MediaType.APPLICATION_JSON)
            .body(response);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostReadResponse> readDetailByPostId(@PathVariable("postId") Long postId) {
        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(postService.getDatailByPostId(postId));
    }

    @GetMapping
    public ResponseEntity<List<PostReadResponse>> readAll() {
        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(postService.getAll());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse<String>> handleException(
        Exception e
    ) {
        return ResponseEntity.internalServerError()
            .body(ExceptionResponse.from(e));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public <T extends Exception> ResponseEntity<ExceptionResponse<String>> handleRuntimeException(
        T e
    ) {
        return ResponseEntity.badRequest()
            .body(ExceptionResponse.from(e));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ExceptionResponse<String>> handleNoSuchElementException(
        NoSuchElementException e
    ) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ExceptionResponse.from(e));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse<List<FieldErrorDetail>>> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException e
    ) {
        return ResponseEntity.badRequest()
            .body(ExceptionResponse.from(e));
    }

}
