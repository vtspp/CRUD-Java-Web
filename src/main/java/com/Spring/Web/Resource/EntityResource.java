package com.Spring.Web.Resource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.Spring.Web.Model.Paciente;
import com.Spring.Web.Repository.EntityRepository;

@Controller
public class EntityResource {

	@Autowired
	private EntityRepository entityRepository;

	@PostMapping("/salvar")
	public String salvarListaCSV(@RequestParam("file") MultipartFile file) throws IOException {
		//Falta criar a classe de serviço. Feito na classe de resource apenas para testes iniciais.
		//Será usada uma pasta temporaria do sistema para receber o arquivo. O mesmo deverá ser apagado após a instância do objeto.
		String upLoadDir = "C:\\sts-bundle\\sts-3.9.9.RELEASE\\Project\\Hospital";
		
		Path copyLocation = Paths
				.get(upLoadDir
						+ File.separator 
						+ StringUtils
						.cleanPath(file.getOriginalFilename()));
		
		Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);

		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(copyLocation.toString()))) {

			String linha = bufferedReader.readLine();
			while (linha != null) {

				String[] vet = linha.split(",");

				Paciente paciente = new Paciente(null, vet[0], Integer.parseInt(vet[1]), Integer.parseInt(vet[2]));

				entityRepository.save(paciente);

				linha = bufferedReader.readLine();
			}
		} catch (FileNotFoundException e) {//falta implementar as Exception personalizadas
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "redirect:carregar";

	}
	
	@GetMapping("/index")
	public String menu() {
		return "index";
	}
	
	@GetMapping("/carregar")
	public String carregarCSV() {
		return "carregarCSV";
	}
	
	@GetMapping("/novo")
	public String novoPaciente() {
		return "cadastrar";
	}
	
	@GetMapping("/listar")
	public String listaPacientes(Model model) {
		Iterable<Paciente> pacientes = entityRepository.findAll();
		model.addAttribute("pacientes", pacientes);
		return "tabelaSql";
	}
	
	@PostMapping("/cadastrar")
	public String salvarPaciente(Paciente paciente) {
		entityRepository.save(paciente);
		return "redirect:novo";
	}
	
	@GetMapping("/deletar")
	public void deletarPaciente(Paciente paciente) {
		entityRepository.deleteAll();
	}
	
	
	//testes
	@GetMapping("/teste")
	public String testeMaterialize(){
		return "teste";
	}
	
	@GetMapping("/acesso")
	public String acesso() {
		return "acesso";
	}

}
