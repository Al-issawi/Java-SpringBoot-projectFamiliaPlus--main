// Función para mostrar el modal de contraseña olvidada
function modalForm1() {
  const modal = document.getElementById("modalForm1");
  if (modal) {
    modal.style.display = "flex";
    document.body.style.overflow = "hidden";
  }
}

// Función para cerrar el modal
function closeModal() {
  const modal = document.getElementById("modalForm1");
  if (modal) {
    modal.style.display = "none";
    document.body.style.overflow = "auto";
  }
}

// Cerrar modal al hacer clic fuera del contenido
window.onclick = function (event) {
  const modal = document.getElementById("modalForm1");
  if (event.target === modal) {
    closeModal();
  }
};

// Funciones para mejorar la experiencia del usuario
document.addEventListener("DOMContentLoaded", function () {
  // Añadir animación suave al scroll
  const links = document.querySelectorAll('a[href^="#"]');
  links.forEach((link) => {
    link.addEventListener("click", function (e) {
      e.preventDefault();
      const target = document.querySelector(this.getAttribute("href"));
      if (target) {
        target.scrollIntoView({ behavior: "smooth" });
      }
    });
  });

  // Validación del formulario
  const loginForm = document.querySelector(".login-form");
  if (loginForm) {
    loginForm.addEventListener("submit", function (e) {
      const usuario = document.getElementById("idUsuario").value.trim();
      const contraseña = document.getElementById("pass").value.trim();

      if (!usuario || !contraseña) {
        e.preventDefault();
        alert("Por favor, complete todos los campos.");
      }
    });
  }
});
