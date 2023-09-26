package es.mdef.apigatel.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.mdef.apigatel.entidades.ModeloConId;
import jakarta.transaction.Transactional;

public interface ModeloRepositorio extends JpaRepository<ModeloConId, Long> {

	// método personalizado. al ofertar NoInventariables, se bonificará con un 10%
	// del total de milis no inventariables ofertaos
	// utilizamos calesce para que si no hay valores, devuelva 0
//	@Query(value="SELECT COALESCE(round(sum(milis)*0.1), 0) FROM public.materiales "
//						+ "WHERE dptoo_Id = :param AND tipo_material = 'N'", nativeQuery = true) 
//	int calcularBonificacion(@Param("param") long dptoOferta);

	
	@Modifying
	@Transactional
	@Query(value = "UPDATE public.modelos SET " + "tipo_modelo = 'E'," +
	"marca = :marca, " +
	"categoria = :categoria, " +
	"nombre_modelo = :nombreModelo, " +
	"detalles = :detalles, " +
	"imagen = :imagen, " +
	"img_reducida = :img_reducida, " +
	"memoria = :memoria, " + 
	"disco_duro = :discoDuro, " + 
	"sistema_operativo = :sistemaOperativo, " + 
	"pulgadas = :pulgadas, " + 
	"conexion = null ," +
	"stereo = null ," +
	"resolucion = null, " +
	"tipo_equipo_informatico = :tipoEquipoInformatico " +
	"WHERE id = :id", nativeQuery = true) 
	void actualizarEquipo( 
			  @Param("marca") String marca,	  
			  @Param("categoria") String categoria,	  
			  @Param("nombreModelo") String nombreModelo,	  
			  @Param("detalles") String detalles,	  
			  @Param("imagen") String imagen,	  
			  @Param("img_reducida") String img_reducida,	  
			  @Param("memoria") Integer memoria,	  
			  @Param("discoDuro") Integer discoDuro,	  
			  @Param("sistemaOperativo") String sistemaOperativo,	  
			  @Param("pulgadas") Integer pulgadas,	  
			  @Param("tipoEquipoInformatico") String tipoEquipoInformatico,	  
			  @Param("id") Long id 
	  );

	@Modifying
	@Transactional
	@Query(value = "UPDATE public.modelos SET " + "tipo_modelo = 'A'," +
	"marca = :marca, " +
	"categoria = :categoria, " +
	"nombre_modelo = :nombreModelo, " +
	"detalles = :detalles, " +
	"imagen = :imagen, " +
	"img_reducida = :img_reducida, " +
	"memoria = null, " + 
	"disco_duro = null, " + 
	"sistema_operativo = null, " + 
	"tipo_equipo_informatico = null, " + 
	"pulgadas = null, " + 
	"conexion = :conexion ," +
	"stereo = :stereo ," +
	"resolucion = null " +
	"WHERE id = :id", nativeQuery = true) 
	void actualizarAuriculares( 
			  @Param("marca") String marca,	  
			  @Param("categoria") String categoria,	  
			  @Param("nombreModelo") String nombreModelo,	  
			  @Param("detalles") String detalles,	  
			  @Param("imagen") String imagen,	  
			  @Param("img_reducida") String img_reducida,	  
			  @Param("stereo") Boolean stereo,	  
			  @Param("conexion") String conexion,	  
			  @Param("id") Long id 
	);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE public.modelos SET " + "tipo_modelo = 'W'," +
	"marca = :marca, " +
	"categoria = :categoria, " +
	"nombre_modelo = :nombreModelo, " +
	"detalles = :detalles, " +
	"imagen = :imagen, " +
	"img_reducida = :img_reducida, " +
	"memoria = null, " + 
	"disco_duro = null, " + 
	"sistema_operativo = null, " + 
	"pulgadas = null, " + 
	"conexion = null ," +
	"stereo = null ," +
	"resolucion = :resolucion, " +
	"tipo_equipo_informatico = null, " + 
	"WHERE id = :id", nativeQuery = true) 
	  void actualizarWebCam( 
			  @Param("marca") String marca,	  
			  @Param("categoria") String categoria,	  
			  @Param("nombreModelo") String nombreModelo,	  
			  @Param("detalles") String detalles,	  
			  @Param("imagen") String imagen,	  
			  @Param("img_reducida") String img_reducida,	  
			  @Param("resolucion") Integer resolucion,	  
			  @Param("id") Long id 
	  );


	  @Query(value = "SELECT * FROM public.modelos " + "WHERE id < 5", nativeQuery
	  = true) List<ModeloConId> algunos();
	 
}
