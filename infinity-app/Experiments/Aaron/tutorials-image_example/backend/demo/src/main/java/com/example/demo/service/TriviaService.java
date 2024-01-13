package com.example.demo.service;

import com.example.demo.model.Trivia;
import com.example.demo.repository.TriviaRepository;
import com.example.demo.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class TriviaService {

    // replace this! careful with the operating system in use
    private static String directory = "C:\\\\Users\\\\hobian\\\\Desktop\\\\trivia\\\\";

    @Autowired
    TriviaRepository repository;

    public List<Trivia> getAllTrivia(){
        return repository.findAll();
    }

    /**
     * return the trivia by id
     * @param id
     * @return
     */
    public Trivia getTriviaById(Long id){
        Optional<Trivia> optinalEntity =  repository.findById(id);
        Trivia trivia = optinalEntity.get();
        return trivia;
    }

    /**
     * return the trivia image by id
     * @param id
     * @return image as byte[]
     * @throws IOException
     */
    public byte[] getTriviaImageById(Long id) throws IOException {
        Optional<Trivia> optinalEntity =  repository.findById(id);
        Trivia trivia = optinalEntity.get();
        String file = trivia.getImagePath();
        byte[] image = FileUtil.loadFile(file);
        return image;
    }

    /**
     * upload trivia with image included
     * @param question
     * @param answer
     * @param base64img
     * @return
     * @throws IOException
     */
    public Trivia postTriviaWithImage(String question, String answer, String base64img) throws IOException {
        Trivia newTrivia = new Trivia(question, answer);
        String fName = question + ".jpg";
        FileUtil.saveFile(base64img, directory, fName);
        newTrivia.setImagePath(directory+fName);
        repository.save(newTrivia);
        return newTrivia;
    }

    /**
     * delete all trivia
     * @return
     */
    public List<Trivia> deleteAllTrivia(){
        repository.deleteAll();
        return repository.findAll();
    }
}
