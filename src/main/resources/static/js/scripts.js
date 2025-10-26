//Enhanced Modal functionality with better UX
function modalForm1() {
  const modal = document.getElementById("modalForm1");
  const contenedor = document.getElementById("contenedor");

  if (modal && contenedor) {
    modal.style.display = "block";
    contenedor.style.overflow = "hidden";
    document.body.style.overflow = "hidden"; // Prevent body scroll

    // Add content to modal
    const mensaje = document.getElementById("mensaje");
    if (mensaje) {
      mensaje.innerHTML =
        "Póngase en contacto con el administrador para recuperar su contraseña.";
    }

    // Focus trap for accessibility
    modal.focus();

    // Close on Escape key
    document.addEventListener("keydown", handleEscapeKey);

    // Close on outside click
    modal.addEventListener("click", handleOutsideClick);
  }
}

//Enhanced function to close modal
function cerrarF() {
  const modal = document.getElementById("modalForm1");
  const contenedor = document.getElementById("contenedor");

  if (modal && contenedor) {
    modal.style.display = "none";
    contenedor.style.overflow = "auto";
    document.body.style.overflow = "auto"; // Restore body scroll

    // Remove event listeners
    document.removeEventListener("keydown", handleEscapeKey);
    modal.removeEventListener("click", handleOutsideClick);
  }
}

// Close modal on Escape key
function handleEscapeKey(event) {
  if (event.key === "Escape") {
    cerrarF();
  }
}

// Close modal on outside click
function handleOutsideClick(event) {
  const modalContent = document.getElementById("modalForm2");
  if (
    event.target === event.currentTarget &&
    !modalContent.contains(event.target)
  ) {
    cerrarF();
  }
}

// Form validation with enhanced UX
function validateForm() {
  const usuario = document.getElementById("idUsuario");
  const password = document.getElementById("pass");
  let isValid = true;

  // Remove existing error classes
  removeErrorClasses();

  if (!usuario.value.trim()) {
    showFieldError(usuario, "El usuario es obligatorio");
    isValid = false;
  }

  if (!password.value.trim()) {
    showFieldError(password, "La contraseña es obligatoria");
    isValid = false;
  }

  return isValid;
}

// Show field error with styling
function showFieldError(field, message) {
  field.classList.add("error");

  // Create or update error message
  let errorMsg = field.parentNode.querySelector(".error-message");
  if (!errorMsg) {
    errorMsg = document.createElement("span");
    errorMsg.className = "error-message";
    field.parentNode.insertBefore(errorMsg, field.nextSibling);
  }
  errorMsg.textContent = message;
}

// Remove error classes and messages
function removeErrorClasses() {
  const errorFields = document.querySelectorAll(".error");
  const errorMessages = document.querySelectorAll(".error-message");

  errorFields.forEach((field) => field.classList.remove("error"));
  errorMessages.forEach((msg) => msg.remove());
}

// Enhanced loading state for form submission
function showLoading(button) {
  const originalText = button.textContent;
  button.innerHTML = '<span class="loading"></span> Entrando...';
  button.disabled = true;

  // Store original text for restoration
  button.dataset.originalText = originalText;
}

function hideLoading(button) {
  button.innerHTML = button.dataset.originalText || "Entrar";
  button.disabled = false;
}

// Smooth animations when elements come into view
function observeElements() {
  const observer = new IntersectionObserver(
    (entries) => {
      entries.forEach((entry) => {
        if (entry.isIntersecting) {
          entry.target.style.opacity = "1";
          entry.target.style.transform = "translateY(0)";
        }
      });
    },
    {
      threshold: 0.1,
    }
  );

  // Observe elements that should animate in
  const animatedElements = document.querySelectorAll(
    ".contenedorLogin, #encabezado"
  );
  animatedElements.forEach((el) => {
    el.style.opacity = "0";
    el.style.transform = "translateY(20px)";
    el.style.transition = "opacity 0.6s ease, transform 0.6s ease";
    observer.observe(el);
  });
}

// Initialize enhanced functionality when DOM is loaded
document.addEventListener("DOMContentLoaded", function () {
  // Initialize animations
  observeElements();

  // Add form validation
  const form = document.querySelector("form");
  if (form) {
    form.addEventListener("submit", function (e) {
      if (!validateForm()) {
        e.preventDefault();
        return false;
      }

      // Show loading state
      const submitButton = form.querySelector('button[type="submit"]');
      if (submitButton) {
        showLoading(submitButton);
      }
    });
  }

  // Add input event listeners for real-time validation
  const inputs = document.querySelectorAll(
    'input[type="text"], input[type="password"]'
  );
  inputs.forEach((input) => {
    input.addEventListener("input", function () {
      if (this.classList.contains("error") && this.value.trim()) {
        this.classList.remove("error");
        const errorMsg = this.parentNode.querySelector(".error-message");
        if (errorMsg) errorMsg.remove();
      }
    });
  });

  // Add keyboard navigation improvements
  document.addEventListener("keydown", function (e) {
    // Allow Tab navigation in modal
    if (e.key === "Tab") {
      const modal = document.getElementById("modalForm1");
      if (modal && modal.style.display === "block") {
        // Implement focus trapping logic here if needed
      }
    }
  });
});
