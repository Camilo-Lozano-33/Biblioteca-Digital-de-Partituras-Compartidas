import 'package:flutter/material.dart';
import 'api_service.dart';

class PartiturasScreen extends StatefulWidget {
  const PartiturasScreen({super.key});

  @override
  State<PartiturasScreen> createState() => _PartiturasScreenState();
}

class _PartiturasScreenState extends State<PartiturasScreen> {
  late Future<List<dynamic>> _partiturasFuture;

  @override
  void initState() {
    super.initState();
    _partiturasFuture = ApiService().fetchPartituras();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Listado de Partituras')),
      body: FutureBuilder<List<dynamic>>(
        future: _partiturasFuture,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(child: CircularProgressIndicator());
          } else if (snapshot.hasError) {
            return Center(child: Text('Error: \\${snapshot.error}'));
          } else if (!snapshot.hasData || snapshot.data!.isEmpty) {
            return const Center(child: Text('No hay partituras disponibles.'));
          }
          final partituras = snapshot.data!;
          return ListView.builder(
            itemCount: partituras.length,
            itemBuilder: (context, index) {
              final partitura = partituras[index];
              return ListTile(
                title: Text(partitura['titulo'] ?? 'Sin título'),
                subtitle: Text(partitura['autor'] ?? ''),
                trailing: IconButton(
                  icon: const Icon(Icons.download),
                  onPressed: () {
                    // Aquí puedes llamar a ApiService().downloadPartitura(partitura['id'])
                  },
                ),
              );
            },
          );
        },
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () => Navigator.pushNamed(context, '/upload'),
        tooltip: 'Subir Partitura',
        child: const Icon(Icons.upload_file),
      ),
    );
  }
}
