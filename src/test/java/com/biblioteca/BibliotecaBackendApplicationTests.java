package com.biblioteca;

import com.biblioteca.model.dto.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BibliotecaBackendApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    private static String adminToken;
    private static String clientToken;
    private static Integer nuevoLibroId;
    private static Integer nuevaCategoriaId;

    private HttpEntity<Void> authHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        return new HttpEntity<>(headers);
    }

    private <T> HttpEntity<T> authBody(T body, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(body, headers);
    }

    // ==================== AUTH ====================

    @Test
    @Order(1)
    void loginAdmin_Success() {
        var request = Map.of("usuario", "admin", "contrasena", "1234");

        ResponseEntity<LoginResponse> response = restTemplate.postForEntity(
                "/api/v1/auth/login", request, LoginResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getToken()).isNotBlank();
        assertThat(response.getBody().getUsuario().getRol()).isEqualTo(3);
        adminToken = response.getBody().getToken();
    }

    @Test
    @Order(2)
    void loginCliente_Success() {
        var request = Map.of("usuario", "jperez", "contrasena", "1234");

        ResponseEntity<LoginResponse> response = restTemplate.postForEntity(
                "/api/v1/auth/login", request, LoginResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getToken()).isNotBlank();
        assertThat(response.getBody().getUsuario().getRol()).isEqualTo(1);
        clientToken = response.getBody().getToken();
    }

    @Test
    @Order(3)
    void login_InvalidCredentials() {
        var request = Map.of("usuario", "admin", "contrasena", "wrong");

        ResponseEntity<ErrorResponse> response = restTemplate.postForEntity(
                "/api/v1/auth/login", request, ErrorResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @Order(4)
    void register_Success() {
        var request = Map.of(
                "usuario", "testuser",
                "contrasena", "1234",
                "nombre", "Test",
                "apellido", "Usuario");

        ResponseEntity<LoginResponse> response = restTemplate.postForEntity(
                "/api/v1/auth/register", request, LoginResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getToken()).isNotBlank();
        assertThat(response.getBody().getUsuario().getUsuario()).isEqualTo("testuser");
        assertThat(response.getBody().getUsuario().getRol()).isEqualTo(1);
    }

    // ==================== CATEGORIAS ====================

    @Test
    @Order(5)
    void categorias_Listar() {
        ResponseEntity<CategoriaResponse[]> response = restTemplate.getForEntity(
                "/api/v1/categorias", CategoriaResponse[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isGreaterThanOrEqualTo(4);
    }

    @Test
    @Order(6)
    void categorias_Crear_ConAdmin() {
        var request = Map.of("nombreCategoria", "Autoayuda", "descripcion", "Libros de autoayuda");

        ResponseEntity<CategoriaResponse> response = restTemplate.exchange(
                "/api/v1/categorias", HttpMethod.POST,
                authBody(request, adminToken), CategoriaResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getNombreCategoria()).isEqualTo("Autoayuda");
        nuevaCategoriaId = response.getBody().getIdCategoria();
    }

    @Test
    @Order(7)
    void categorias_Crear_SinAuth_Retorna403() {
        var request = Map.of("nombreCategoria", "SinAuth");

        ResponseEntity<String> response = restTemplate.postForEntity(
                "/api/v1/categorias", request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    // ==================== LIBROS ====================

    @Test
    @Order(8)
    void libros_Listar_Publico() {
        ResponseEntity<LibroResponse[]> response = restTemplate.getForEntity(
                "/api/v1/libros", LibroResponse[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isGreaterThanOrEqualTo(5);
    }

    @Test
    @Order(9)
    void libros_Listar_Filtro() {
        ResponseEntity<LibroResponse[]> response = restTemplate.getForEntity(
                "/api/v1/libros?busqueda=Clean", LibroResponse[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isEqualTo(1);
        assertThat(response.getBody()[0].getTitulo()).contains("Clean");
    }

    @Test
    @Order(10)
    void libros_ObtenerPorId() {
        ResponseEntity<LibroResponse> response = restTemplate.getForEntity(
                "/api/v1/libros/1", LibroResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTitulo()).isNotBlank();
    }

    @Test
    @Order(11)
    void libros_ObtenerPorId_NoExiste() {
        ResponseEntity<ErrorResponse> response = restTemplate.getForEntity(
                "/api/v1/libros/999", ErrorResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @Order(12)
    void libros_Crear_ConAdmin() {
        var request = Map.of(
                "titulo", "Libro Test",
                "autor", "Autor Test",
                "anoPublicacion", 2024,
                "precio", 29.90,
                "stock", 10);

        ResponseEntity<LibroResponse> response = restTemplate.exchange(
                "/api/v1/libros", HttpMethod.POST,
                authBody(request, adminToken), LibroResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTitulo()).isEqualTo("Libro Test");
        nuevoLibroId = response.getBody().getIdLibro();
    }

    @Test
    @Order(13)
    void libros_Crear_SinAuth_Retorna403() {
        var request = Map.of(
                "titulo", "Sin Auth",
                "autor", "NA",
                "precio", 10,
                "stock", 1);

        ResponseEntity<String> response = restTemplate.postForEntity(
                "/api/v1/libros", request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @Order(14)
    void libros_Actualizar_ConAdmin() {
        var request = Map.of(
                "titulo", "Libro Test Editado",
                "autor", "Autor Test",
                "anoPublicacion", 2024,
                "precio", 35.00,
                "stock", 20);

        ResponseEntity<LibroResponse> response = restTemplate.exchange(
                "/api/v1/libros/" + nuevoLibroId, HttpMethod.PUT,
                authBody(request, adminToken), LibroResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTitulo()).isEqualTo("Libro Test Editado");
        assertThat(response.getBody().getPrecio()).isEqualByComparingTo(BigDecimal.valueOf(35.00));
    }

    @Test
    @Order(15)
    void libros_Eliminar_ConAdmin() {
        ResponseEntity<Void> response = restTemplate.exchange(
                "/api/v1/libros/" + nuevoLibroId, HttpMethod.DELETE,
                authHeaders(adminToken), Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @Order(16)
    void libros_Eliminar_NoExiste() {
        ResponseEntity<ErrorResponse> response = restTemplate.exchange(
                "/api/v1/libros/999", HttpMethod.DELETE,
                authHeaders(adminToken), ErrorResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    // ==================== USUARIOS ====================

    @Test
    @Order(17)
    void usuarios_Listar_ConAdmin() {
        ResponseEntity<UsuarioResponse[]> response = restTemplate.exchange(
                "/api/v1/usuarios", HttpMethod.GET,
                authHeaders(adminToken), UsuarioResponse[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isGreaterThanOrEqualTo(2);
    }

    @Test
    @Order(18)
    void usuarios_Listar_SinAuth_Retorna403() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                "/api/v1/usuarios", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    // ==================== BOLETAS ====================

    @Test
    @Order(19)
    void boletas_Crear_ComoCliente() {
        var detalle = Map.of("idLibro", 1, "cantidad", 2);
        var request = Map.of("detalles", List.of(detalle));

        ResponseEntity<BoletaResponse> response = restTemplate.exchange(
                "/api/v1/boletas", HttpMethod.POST,
                authBody(request, clientToken), BoletaResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTotal()).isNotNull();
        assertThat(response.getBody().getDetalles()).hasSize(1);
        assertThat(response.getBody().getDetalles().get(0).getCantidad()).isEqualTo(2);
    }

    @Test
    @Order(20)
    void boletas_MisBoletas() {
        ResponseEntity<BoletaResponse[]> response = restTemplate.exchange(
                "/api/v1/boletas/mis-boletas", HttpMethod.GET,
                authHeaders(clientToken), BoletaResponse[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isGreaterThanOrEqualTo(1);
    }

    @Test
    @Order(21)
    void boletas_StockInsuficiente_Retorna400() {
        var detalle = Map.of("idLibro", 1, "cantidad", 9999);
        var request = Map.of("detalles", List.of(detalle));

        ResponseEntity<ErrorResponse> response = restTemplate.exchange(
                "/api/v1/boletas", HttpMethod.POST,
                authBody(request, clientToken), ErrorResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
