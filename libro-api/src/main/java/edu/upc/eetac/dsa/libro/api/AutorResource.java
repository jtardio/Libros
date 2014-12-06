package edu.upc.eetac.dsa.libro.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import edu.upc.eetac.dsa.libro.api.modelos.Autores;
import edu.upc.eetac.dsa.libro.api.modelos.Libros;

@Path("/autores")
public class AutorResource {
	private DataSource ds = DataSourceSPA.getInstance().getDataSource();
	@Context
	private SecurityContext security;
	
	private String DELETE_AUTOR_QUERY="DELETE  FROM autores where nombreautor=?";
	
	@DELETE
	@Path("/{nombreautor}")
	public void deleteSting(@PathParam("nombreautor") String nombreautor) {
		//validateUser(stingid);
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}

		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(DELETE_AUTOR_QUERY);
			stmt.setString(1,nombreautor);

			int rows = stmt.executeUpdate();
			if (rows == 0)
				throw new NotFoundException("esto no existe un autor con este nombre="
						+ nombreautor);// Deleting inexistent sting
		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
	}
	private String GET_AUTORES_BY_NOMBRE = "select * from autores where nombreautor=?";
	
	private Autores getAutoresFromDatabase(String nombreautor) {
		Autores autor = new Autores();
		Connection conn = null;

		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(GET_AUTORES_BY_NOMBRE);
			stmt.setString(1, nombreautor);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				autor.setNombreautor(rs.getString("nombreautor"));
			} else {
				throw new NotFoundException(
						"There's no libro with nombreautor=" + nombreautor);
			}

		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}

		return autor;
	}
	
	
	
	
private void validateAutor( Autores autor){
		
		if ( autor.getNombreautor()==null){
			throw new BadRequestException("nombre no puede ser null");
		}
}
private String INSERT_AUTOR_QUERY = "insert into autores values (?)"; 

	@POST
	@Consumes(MediaType.LIBRO_API_AUTORES)
	@Produces(MediaType.LIBRO_API_AUTORES)
	public Autores createAutores(Autores autor) {
		validateAutor(autor);
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}

		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(INSERT_AUTOR_QUERY,
					Statement.RETURN_GENERATED_KEYS);

			//stmt.setString(1, security.getUserPrincipal().getName());
			stmt.setString(1, autor.getNombreautor());
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				String nombreautores = rs.getString(1);

				autor = getAutoresFromDatabase(nombreautores);
			} else {
				// Something has failed...
			}
			
		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}

		return autor;
		
	}
	
	private String UPDATE_AUTOR_QUERY = "update autores set nombreautor=ifnull(?, nombreautor) where nombreautor = ?";
	
	@PUT
	@Path("/{nombreautor}")
	@Consumes(MediaType.LIBRO_API_AUTORES)
	@Produces(MediaType.LIBRO_API_AUTORES)
	public Autores updateautores(@PathParam("nombreautor") String nombreautor,
			Autores autor) {

		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(UPDATE_AUTOR_QUERY);
			stmt.setString(1, autor.getNombreautor());
			stmt.setString(2, nombreautor);

			int rows = stmt.executeUpdate();
			if (rows == 1)
				autor = getAutoresFromDatabase(autor.getNombreautor());
			else {
				throw new NotFoundException(
						"There's no autor with nombreautor=" + nombreautor);
			}

		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}

		return autor;
	}
		
	
}