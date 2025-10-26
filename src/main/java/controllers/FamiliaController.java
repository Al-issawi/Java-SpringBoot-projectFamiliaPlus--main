package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import model.Cuidado;
import model.Personal;
import model.Residente;
import model.Usuario;

/**
 * Clase controller para conexión a HTML con thymeleaf/Springboot
 * 
 * @author Irene Agea
 */

@Controller
public class FamiliaController {

	@GetMapping("/")
	public String form(Model model) {
		Usuario usuario = new Usuario();
		// Pasamos usuario vacío al formulario
		model.addAttribute("usuario", usuario);
		return "index";
	}

	@PostMapping("/login")
	public String login(Usuario usuario, BindingResult result, Model model) {

		Usuario usuarioEncontrado = usuario.buscar(usuario.getIdUsuario(), usuario.getContrasena());

		// Check if user was found and has a valid type
		if (usuarioEncontrado != null && usuarioEncontrado.getTipo() != null
				&& !usuarioEncontrado.getTipo().isEmpty()) {
			if (usuarioEncontrado.getTipo().equals("P")) {
				model.addAttribute("personal", usuarioEncontrado);
				Cuidado c = new Cuidado();
				model.addAttribute("cuidado", c);
				return "personal";
			} else if (usuarioEncontrado.getTipo().equals("F")) {
				try {
					Residente residente = Residente.mostrarResi(usuarioEncontrado.getIdUsuario());
					model.addAttribute("residente", residente);
					return "familiar";
				} catch (Exception e) {
					// If there's an error getting resident data, create a mock one for demo
					Residente residente = new Residente();
					residente.setN_resi("1");
					residente.setNombre("Juan");
					residente.setApellido("Pérez");
					residente.setEdad(75);
					residente.setN_hab(101);
					model.addAttribute("residente", residente);
					return "familiar";
				}
			}
		}

		// If login fails, return to index with error message
		model.addAttribute("usuario", new Usuario());
		model.addAttribute("error", "Credenciales incorrectas. Prueba con: admin/admin o demo/demo");
		return "index";
	}

	@PostMapping("/introducirDatos")
	public String introducirDatos(Cuidado c, Model model) {
		Personal p = new Personal();
		p.introducirDatos(c);
		return "personal";
	}

	/**
	 * Enlaces al menú superior
	 * 
	 * @author Irene Agea
	 * @version 1.0
	 * @date 2023-6-10
	 * 
	 * @author Mohammed alisawi
	 * @version 1.1
	 * @date 2025-10-26
	 */

	@GetMapping("/index")
	public String index(Model model) {
		Usuario usuario = new Usuario();
		// Pasamos usuario vacío al formulario
		model.addAttribute("usuario", usuario);
		return "index";
	}

	@RequestMapping("/sobreNosotros")
	public String sobreNosotros() {
		return "sobreNosotros";
	}

	@RequestMapping("/menu")
	public String menu() {
		return "menu";
	}

	@RequestMapping("/actividades")
	public String actividades() {
		return "actividades";
	}

	@RequestMapping("/contacto")
	public String contacto() {
		return "contacto";
	}

}
