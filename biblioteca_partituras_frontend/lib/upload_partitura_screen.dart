import 'package:flutter/material.dart';
import 'api_service.dart';
import 'package:file_picker/file_picker.dart';

class UploadPartituraScreen extends StatefulWidget {
  const UploadPartituraScreen({super.key});

  @override
  State<UploadPartituraScreen> createState() => _UploadPartituraScreenState();
}

class _UploadPartituraScreenState extends State<UploadPartituraScreen> {
  final TextEditingController _tituloController = TextEditingController();
  final TextEditingController _autorController = TextEditingController();
  String? _filePath;
  bool _loading = false;
  String? _error;

  void _pickFile() async {
    final result = await FilePicker.platform.pickFiles();
    if (result != null && result.files.single.path != null) {
      setState(() {
        _filePath = result.files.single.path;
      });
    }
  }

  void _upload() async {
    if (_filePath == null) {
      setState(() { _error = 'Selecciona un archivo.'; });
      return;
    }
    setState(() { _loading = true; _error = null; });
    try {
      await ApiService().uploadPartitura(
        _tituloController.text,
        _autorController.text,
        _filePath!,
      );
      Navigator.pop(context);
    } catch (e) {
      setState(() { _error = e.toString(); });
    } finally {
      setState(() { _loading = false; });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Subir Partitura')),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            TextField(
              controller: _tituloController,
              decoration: const InputDecoration(labelText: 'Título'),
            ),
            TextField(
              controller: _autorController,
              decoration: const InputDecoration(labelText: 'Autor'),
            ),
            const SizedBox(height: 16),
            ElevatedButton(
              onPressed: _pickFile,
              child: Text(_filePath == null ? 'Seleccionar archivo' : 'Archivo seleccionado'),
            ),
            if (_error != null) Text(_error!, style: const TextStyle(color: Colors.red)),
            const SizedBox(height: 16),
            ElevatedButton(
              onPressed: _loading ? null : _upload,
              child: _loading ? const CircularProgressIndicator() : const Text('Subir'),
            ),
          ],
        ),
      ),
    );
  }
}
