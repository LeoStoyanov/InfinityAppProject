package com.example.demo.controller;

import com.example.demo.model.Trivia;
import com.example.demo.service.TriviaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@RestController
public class TriviaController {

    @Autowired
    TriviaService service;

    @GetMapping("trivia/all")
    List<Trivia> getAllTrivia(){
        return service.getAllTrivia();
    }

    @GetMapping("trivia/{id}")
    Trivia getTriviaById(@PathVariable Long id){
        return service.getTriviaById(id);
    }

    @GetMapping("trivia/image/{id}")
    byte[] getImageById(@PathVariable Long id) throws IOException {
        return service.getTriviaImageById(id);
    }

    @PostMapping("trivia/postwithimage")
    public Trivia postTrivia(@RequestParam("question") String question,
                             @RequestParam("answer") String answer,
                             @RequestParam("image") String base64img) throws IOException {
        return service.postTriviaWithImage(question, answer, base64img);
    }

    @DeleteMapping("trivia/all/delete")
    List<Trivia> deleteAllTrivia(){
        return service.deleteAllTrivia();
    }

}
