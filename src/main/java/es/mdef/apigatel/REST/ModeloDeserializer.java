package es.mdef.apigatel.REST;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import es.mdef.apigatel.entidades.ModeloConId;
import es.mdef.apigatel.repositorios.ModeloRepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class ModeloDeserializer extends JsonDeserializer<ModeloConId> {
    @Autowired
    private ModeloRepositorio modeloRepository;

    @Override
    public ModeloConId deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String modeloUrl = jsonParser.getValueAsString();
      
        Long modeloId = extractModeloIdFromUrl(modeloUrl);
        
        ModeloConId modelo = modeloRepository.findById(modeloId)
            .orElseThrow(() -> new RuntimeException("Modelo no encontrado para el ID: " + modeloId));

        return modelo;
    }

    // Método para extraer el ID del modelo de la URL
    private Long extractModeloIdFromUrl(String modeloUrl) {
        try {
            String[] parts = modeloUrl.split("/");
            return Long.parseLong(parts[parts.length - 1]);
        } catch (Exception e) {
            throw new RuntimeException("URL de modelo no válida: " + modeloUrl);
        }
    }
}
