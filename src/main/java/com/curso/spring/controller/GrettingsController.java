package com.curso.spring.controller;


import com.curso.spring.model.User;
import com.curso.spring.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GrettingsController {

    @Autowired // Injeção de dependencia
    private UserRepository userRepository;

    @GetMapping("/listaTodos") // Metodo BUSCAR
    @ResponseBody // retorna os dados para o corpo da resposta
    public ResponseEntity<List<User>> listaUsuario(){
        List<User> usuarios = userRepository.findAll(); //Executa a consulta no Banco de dados.
        return new ResponseEntity<List<User>>(usuarios, HttpStatus.OK); //Retorna lista em JSON
    }

    @GetMapping("/buscarUserId")
    @ResponseBody
    public ResponseEntity<User> buscarId(@RequestParam(value = "iduser") @Valid Long idUser){
        User usuario = userRepository.findById(idUser).get();
        return new ResponseEntity<User>(usuario, HttpStatus.OK);
    }

    @PostMapping("/salvar") // Mapeia a url
    @ResponseBody // Descrição da resposta
    public ResponseEntity<User> salvar(@RequestBody @Valid User user){ // recebe os dados para salvar
        User usuario = userRepository.save(user);
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }

    @PutMapping("/atualizar")
    @ResponseBody
    public ResponseEntity<?> atualizar(@RequestBody @Valid User user){ // Faz um retorno generico.
        if(user.getId() == null){
            return new ResponseEntity<String>("ID não foi informado", HttpStatus.OK);
        }
        User usuario = userRepository.saveAndFlush(user); // Ele salva e roda diretamente no banco de dados.
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }

    @GetMapping("/buscarPorNome")
    @ResponseBody
    public ResponseEntity<List<User>> buscarpornome(@RequestBody  @RequestParam(value = "name") @Valid String name){
        List<User> usuarios = userRepository.buscarPorNome(name.trim().toUpperCase()); // tirar os espaços atraves do trim
        return new ResponseEntity<List<User>>(usuarios, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    @ResponseBody
    public ResponseEntity<String> deletar(@RequestParam @Valid Long idUser){
        userRepository.deleteById(idUser);
        return new ResponseEntity<String>("Usuario deletado com sucesso", HttpStatus.OK);
    }
}
