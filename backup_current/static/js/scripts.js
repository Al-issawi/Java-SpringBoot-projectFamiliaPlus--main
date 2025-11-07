//Función del modal del formulario
function modalForm1(){
document.getElementById("modalForm1").style.display="block";
document.getElementById("contenedor").style.overflow="hidden";

var mensaje;
mensaje = "Póngase en contacto con el administrador";

document.getElementById("mensaje").innerHTML= mensaje;
}

//Función para cerrar el modal del formulario
function cerrarF(){
document.getElementById("modalForm1").style.display="none";
document.getElementById("contenedor").style.overflow="auto";
}

