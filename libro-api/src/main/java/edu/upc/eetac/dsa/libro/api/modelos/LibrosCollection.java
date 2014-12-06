package edu.upc.eetac.dsa.libro.api.modelos;


import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Link;

import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import org.glassfish.jersey.linking.InjectLink.Style;

import edu.upc.eetac.dsa.libro.api.LibrosResource;
import edu.upc.eetac.dsa.libro.api.MediaType;
public class LibrosCollection {

	
	@InjectLinks({
		@InjectLink(resource = LibrosResource.class, style = Style.ABSOLUTE, rel = "create-libro", title = "Create libro", type = MediaType.LIBRO_API_LIBROS),
		@InjectLink(value = "/search/autor/{nombreautor}?before={before}", style = Style.ABSOLUTE, rel = "previous", title = "Previous libros", type = MediaType.LIBRO_API_LIBROS_COLLECTION, bindings = {
				@Binding(name = "before", value = "${instance.oldestTimestamp}"),
				@Binding(name = "nombreautor", value = "${instance.pattern}") }),
		@InjectLink(value = "/search/autor/{nombreautor}?after={after}", style = Style.ABSOLUTE, rel = "current", title = "Newest libros", type = MediaType.LIBRO_API_LIBROS_COLLECTION, bindings = {
				@Binding(name = "after", value = "${instance.newestTimestamp}"),
				@Binding(name = "nombreautor", value = "${instance.pattern}") }),
@InjectLink(value = "/search/titulo/{titulo}?after={after}", style = Style.ABSOLUTE, rel = "current", title = "Newest libros", type = MediaType.LIBRO_API_LIBROS_COLLECTION, bindings = {
		@Binding(name = "after", value = "${instance.newestTimestamp}"),
		@Binding(name = "titulo", value = "${instance.pattern}") }),
@InjectLink(value = "/search/titulo/{titulo}?before={before}", style = Style.ABSOLUTE, rel = "previous", title = "Previous libros", type = MediaType.LIBRO_API_LIBROS_COLLECTION, bindings = {
				@Binding(name = "before", value = "${instance.oldestTimestamp}"),
				@Binding(name = "titulo", value = "${instance.pattern}") })})
private List<Link> links;

public List<Link> getLinks() {
	return links;
}

public void setLinks(List<Link> links) {
	this.links = links;
}

private String pattern;

private List<Libros> libros = new ArrayList<Libros>();
private long NewestTimestamp;
private long OldestTimestamp;

public void addLibros(Libros libro) {
	libros.add(libro);
}

public long getNewestTimestamp() {
	return NewestTimestamp;
}

public void setNewestTimestamp(long newestTimestamp) {
	NewestTimestamp = newestTimestamp;
}

public long getOldestTimestamp() {
	return OldestTimestamp;
}

public void setOldestTimestamp(long oldestTimestamp) {
	OldestTimestamp = oldestTimestamp;
}

public List<Libros> getLibros() {
	return libros;
}

public void setLibros(List<Libros> libros) {
	this.libros = libros;
}

public String getPattern() {
	return pattern;
}

public void setPattern(String pattern) {
	this.pattern = pattern;
}
}
