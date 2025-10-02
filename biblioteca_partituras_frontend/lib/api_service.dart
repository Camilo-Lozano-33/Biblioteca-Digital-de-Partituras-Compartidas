import 'dart:convert';
import 'package:http/http.dart' as http;

class ApiService {
  static const String baseUrl = 'http://10.0.2.2git branch -M main:8060'; // Cambia esto si tu backend usa otra URL

  // Login de usuario
  Future<Map<String, dynamic>> login(String username, String password) async {
    final response = await http.post(
      Uri.parse('$baseUrl/login'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode({'username': username, 'password': password}),
    );
    if (response.statusCode == 200) {
      return jsonDecode(response.body);
    } else {
      throw Exception('Error al iniciar sesión');
    }
  }

  // Registro de usuario
  Future<Map<String, dynamic>> register(String username, String password) async {
    final response = await http.post(
      Uri.parse('$baseUrl/registro'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode({'username': username, 'password': password}),
    );
    if (response.statusCode == 200) {
      return jsonDecode(response.body);
    } else {
      throw Exception('Error al registrar usuario');
    }
  }

  // Obtener listado de partituras
  Future<List<dynamic>> fetchPartituras() async {
    final response = await http.get(Uri.parse('$baseUrl/partituras'));
    if (response.statusCode == 200) {
      return jsonDecode(response.body);
    } else {
      throw Exception('Error al obtener partituras');
    }
  }

  // Subir nueva partitura (requiere multipart/form-data)
  Future<Map<String, dynamic>> uploadPartitura(String titulo, String autor, String filePath) async {
    var request = http.MultipartRequest('POST', Uri.parse('$baseUrl/partituras/subir'));
    request.fields['titulo'] = titulo;
    request.fields['autor'] = autor;
    request.files.add(await http.MultipartFile.fromPath('archivo', filePath));
    var streamedResponse = await request.send();
    var response = await http.Response.fromStream(streamedResponse);
    if (response.statusCode == 200) {
      return jsonDecode(response.body);
    } else {
      throw Exception('Error al subir partitura');
    }
  }

  // Descargar partitura (devuelve bytes)
  Future<http.Response> downloadPartitura(int id) async {
    final response = await http.get(Uri.parse('$baseUrl/partituras/descargar/$id'));
    if (response.statusCode == 200) {
      return response;
    } else {
      throw Exception('Error al descargar partitura');
    }
  }

  // (Opcional) Cerrar sesión (si tu backend lo soporta)
  Future<void> logout() async {
    // Implementa si tu backend tiene endpoint de logout
  }
}
