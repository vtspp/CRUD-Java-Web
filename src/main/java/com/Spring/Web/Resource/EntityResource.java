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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.Spring.Web.Errors.EntityErrors;
import com.Spring.Web.Model.Paciente;
import com.Spring.Web.Repository.EntityRepository;

@Controller
public class EntityResource {

	
	private final EntityRepository entityRepositoryDAO;
	
	@Autowired
	public EntityResource(EntityRepository entityRepositoryDAO) {
		this.entityRepositoryDAO = entityRepositoryDAO;
	}
	
	@PostMapping("cadastrar")
	public String salvarPaciente(@RequestBody Paciente paciente) {
		entityRepositoryDAO.save(paciente);
		return "redirect:novo";
	}
	
	@PutMapping("alterar")
	public Paciente alterarDados (@RequestBody String cpf) {
		
		    verificarCpfExiste(cpf);
			
			Paciente paciente = entityRepositoryDAO.findByCpf(cpf);
			paciente.setNome(paciente.getNome());
			paciente.setRg(paciente.getRg());
			paciente.setNascimento(paciente.getNascimento());
			paciente.setGenero(paciente.getGenero());
			paciente.setCelular(paciente.getCelular());
			paciente.setTelefone(paciente.getTelefone());
			paciente.setEmail(paciente.getEmail());
			paciente.setEndereco(paciente.getEndereco());
			paciente.setCep(paciente.getCep());
			
			return entityRepositoryDAO.save(paciente);
	}
	
	@GetMapping("listar")
	public ModelAndView listaPacientes() {
		ModelAndView model = new ModelAndView("tabelaSql");
		model.addObject("pacientes", entityRepositoryDAO.findAll());
		return model;
	}

	@PostMapping("salvar")
	public String salvarListaCSV(@RequestParam("file") MultipartFile file) throws IOException {
		// Falta criar a classe de serviço. Feito na classe de resource apenas para
		// testes iniciais.
		// Será usada uma pasta temporaria do sistema para receber o arquivo. O mesmo
		// deverá ser apagado após a instância do objeto.
		String upLoadDir = "C:\\sts-bundle\\sts-3.9.9.RELEASE\\Project\\Hospital";

		Path copyLocation = Paths.get(
				upLoadDir 
				+ File.separator 
				+ StringUtils.cleanPath(
						file.getOriginalFilename()));

		Files.copy(
				file.getInputStream()
				, copyLocation
				, StandardCopyOption.REPLACE_EXISTING);

		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(copyLocation.toString()))) {

			String linha = bufferedReader.readLine();
			while (linha != null) {

				String[] vet = linha.split(",");

				// Refatorar isso...
				Paciente paciente = new Paciente(null, vet[0]
						                             , vet[1]
						                             , vet[2]
						                             , vet[3]
						                             , vet[4]
						                             , vet[5]
						                             , vet[6]
						                             , vet[7]
						                             , vet[8]);

				entityRepositoryDAO.save(paciente);

				linha = bufferedReader.readLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "redirect:carregar";

	}

	@DeleteMapping("deletar/cpf")
	public void deletarByCpf(@RequestBody String cpf) {
		verificarCpfExiste(cpf);
		entityRepositoryDAO.deleteByCpf(cpf);
	}
		
	@GetMapping("buscar/nome/{nome}")
	public Paciente buscarByName(@PathVariable String nome) {
		return entityRepositoryDAO.findByNomeIgnoreCaseContaining(nome);
	}
	
	@GetMapping("buscar/cpf/{cpf}")
	public Paciente buscarByCpf(@PathVariable String cpf) {
		verificarCpfExiste(cpf);
		return entityRepositoryDAO.findByCpf(cpf);
	}
	
	@GetMapping("buscar/rg/{rg}")
	public Paciente buscarByRg(@PathVariable String rg) {
		verificarRgExiste(rg);
		return entityRepositoryDAO.findByRg(rg);
	}
	
	private void verificarCpfExiste(String cpf) {
		if (!entityRepositoryDAO.existsByCpf(cpf)) {
			throw new EntityErrors("Cpf " + cpf + " não Encontrado na base de dados");
		}
	}
	
	private void verificarRgExiste(String rg) {
		if (!entityRepositoryDAO.existsByRg(rg)) {
			throw new EntityErrors("Rg " + rg + " não Encontrado na base de dados");
		}
	}

	// Telas
	@GetMapping("/acesso")
	public String telaAcesso() {
		return "telaAcesso";
	}

	@GetMapping("/principal")
	public String telaMenu() {
		return "telaMenu";
	}
	
	@GetMapping("/novo")
	public String telaNovoPaciente() {
		return "telaNovoPaciente";
	}
	
	@GetMapping("/alterarDados")
	public String telaAlterarDados() {
		return "alterarDados";
	}

	@GetMapping("/carregar")
	public String telaCarregarCSV() {
		return "telaCarregarCSV";
	}

	// testes
	@GetMapping("/teste")
	public String teste() {
		return "teste";
	}

}
