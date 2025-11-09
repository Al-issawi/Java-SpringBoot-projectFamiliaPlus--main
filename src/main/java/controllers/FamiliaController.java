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

		Usuario loggedUser = usuario.buscar(usuario.getIdUsuario(), usuario.getContrasena());

		// Check if user was found
		if (loggedUser == null || loggedUser.getTipo() == null) {
			model.addAttribute("error", "Usuario o contraseña incorrectos");
			model.addAttribute("usuario", new Usuario());
			return "index";
		}

		// Check user type based on database values
		if (loggedUser.getTipo().equals("personal")) {
			model.addAttribute("personal", loggedUser);
			Cuidado c = new Cuidado();
			// Pasamos usuario vacío al formulario
			model.addAttribute("cuidado", c);
			return "personal";
		} else if (loggedUser.getTipo().equals("administrador")) {
			model.addAttribute("personal", loggedUser);
			Cuidado c = new Cuidado();
			model.addAttribute("cuidado", c);
			return "personal"; // Admin uses same interface as personal
		} else if (loggedUser.getTipo().equals("familiar")) {
			Residente residente = Residente.mostrarResi(loggedUser.getIdUsuario());
			model.addAttribute("residente", residente);
			return "familiar";
		} else {
			model.addAttribute("error", "Tipo de usuario no válido");
			model.addAttribute("usuario", new Usuario());
			return "index";
		}

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
