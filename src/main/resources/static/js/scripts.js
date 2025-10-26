//Enhanced Modal functionality with better UX
function modalForm1() {
  const modal = document.getElementById("modalForm1");

  if (modal) {
    modal.style.display = "flex";
    modal.classList.add("show");
    document.body.style.overflow = "hidden"; // Prevent body scroll

    // Set aria-hidden to false for accessibility
    modal.setAttribute("aria-hidden", "false");

    // Focus trap for accessibility
    const firstFocusable = modal.querySelector(
      'button, [href], input, select, textarea, [tabindex]:not([tabindex="-1"])'
    );
    if (firstFocusable) {
      firstFocusable.focus();
    }

    // Close on Escape key
    document.addEventListener("keydown", handleEscapeKey);

    // Close on outside click
    modal.addEventListener("click", handleOutsideClick);
  }
}

//Enhanced function to close modal
function cerrarF() {
  const modal = document.getElementById("modalForm1");

  if (modal) {
    modal.classList.remove("show");
    modal.setAttribute("aria-hidden", "true");

    // Add fade out animation
    setTimeout(() => {
      modal.style.display = "none";
      document.body.style.overflow = "auto"; // Restore body scroll
    }, 300);

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

// Enhanced Login Form Functionality
document.addEventListener("DOMContentLoaded", function () {
  const loginForm = document.getElementById("loginForm");
  const userInput = document.getElementById("idUsuario");
  const passwordInput = document.getElementById("pass");
  // const togglePassword = document.getElementById("togglePassword"); // Removed eye icon
  const loginBtn = document.getElementById("loginBtn");

  // Password Toggle Functionality - REMOVED
  // if (togglePassword && passwordInput) {
  //   togglePassword.addEventListener("click", function () {
  //     const type =
  //       passwordInput.getAttribute("type") === "password" ? "text" : "password";
  //     passwordInput.setAttribute("type", type);

  //     // Toggle icon
  //     const icon = this.querySelector("i");
  //     if (type === "text") {
  //       icon.classList.remove("fa-eye");
  //       icon.classList.add("fa-eye-slash");
  //     } else {
  //       icon.classList.remove("fa-eye-slash");
  //       icon.classList.add("fa-eye");
  //     }
  //   });
  // }

  // Real-time validation
  if (userInput) {
    userInput.addEventListener("blur", function () {
      validateField(
        this,
        "usuario-error",
        "Por favor, introduce un nombre de usuario válido"
      );
    });

    userInput.addEventListener("input", function () {
      clearError("usuario-error");
      this.classList.remove("error");
    });
  }

  if (passwordInput) {
    passwordInput.addEventListener("blur", function () {
      validateField(
        this,
        "password-error",
        "La contraseña debe tener al menos 3 caracteres"
      );
    });

    passwordInput.addEventListener("input", function () {
      clearError("password-error");
      this.classList.remove("error");
    });
  }

  // Enhanced form submission
  if (loginForm) {
    loginForm.addEventListener("submit", function (e) {
      e.preventDefault();

      let isValid = true;

      // Validate username
      if (
        !validateField(
          userInput,
          "usuario-error",
          "Por favor, introduce un nombre de usuario válido"
        )
      ) {
        isValid = false;
      }

      // Validate password
      if (
        !validateField(
          passwordInput,
          "password-error",
          "La contraseña debe tener al menos 3 caracteres"
        )
      ) {
        isValid = false;
      }

      if (isValid) {
        showLoading();

        // Simulate brief loading then submit
        setTimeout(() => {
          this.submit(); // Actually submit the form
        }, 800);
      }
    });
  }

  function validateField(field, errorId, message) {
    const errorElement = document.getElementById(errorId);

    if (!field || !field.value.trim() || field.value.length < 3) {
      showError(errorElement, message);
      field.classList.add("error");
      return false;
    } else {
      clearError(errorId);
      field.classList.remove("error");
      return true;
    }
  }

  function showError(errorElement, message) {
    if (errorElement) {
      errorElement.textContent = message;
      errorElement.classList.add("show");
    }
  }

  function clearError(errorId) {
    const errorElement = document.getElementById(errorId);
    if (errorElement) {
      errorElement.textContent = "";
      errorElement.classList.remove("show");
    }
  }

  function showLoading() {
    if (loginBtn) {
      loginBtn.classList.add("loading");
      loginBtn.disabled = true;
    }
  }

  function hideLoading() {
    if (loginBtn) {
      loginBtn.classList.remove("loading");
      loginBtn.disabled = false;
    }
  }
});

// Enhanced Mobile Navigation
document.addEventListener("DOMContentLoaded", function () {
  const navToggle = document.getElementById("navToggle");
  const navMenu = document.getElementById("navMenu");

  if (navToggle && navMenu) {
    navToggle.addEventListener("click", function () {
      this.classList.toggle("active");
      navMenu.classList.toggle("active");

      // Update aria attributes for accessibility
      const isExpanded = navMenu.classList.contains("active");
      this.setAttribute("aria-expanded", isExpanded);
    });

    // Close mobile menu when clicking outside
    document.addEventListener("click", function (event) {
      if (
        !navToggle.contains(event.target) &&
        !navMenu.contains(event.target)
      ) {
        navToggle.classList.remove("active");
        navMenu.classList.remove("active");
        navToggle.setAttribute("aria-expanded", "false");
      }
    });

    // Close mobile menu when clicking on a nav link
    const navLinks = navMenu.querySelectorAll(".nav-link");
    navLinks.forEach((link) => {
      link.addEventListener("click", function () {
        navToggle.classList.remove("active");
        navMenu.classList.remove("active");
        navToggle.setAttribute("aria-expanded", "false");
      });
    });
  }
});
