/*
 * package com.sdx.platform.quartz.service.impl;
 * 
 * import java.io.BufferedReader; import java.io.File; import
 * java.io.IOException; import java.io.InputStream; import
 * java.io.InputStreamReader; import java.io.StringWriter; import
 * java.net.URISyntaxException; import java.nio.charset.Charset; import
 * java.util.List;
 * 
 * import javax.servlet.ServletContext; import javax.ws.rs.Consumes; import
 * javax.ws.rs.GET; import javax.ws.rs.POST; import javax.ws.rs.Path; import
 * javax.ws.rs.Produces; import javax.ws.rs.core.Response;
 * 
 * import org.apache.commons.io.FileUtils; import org.apache.commons.io.IOUtils;
 * import org.bson.json.JsonReader;
 * 
 * import com.sdx.platform.domain.User;
 * 
 * import lombok.extern.slf4j.Slf4j;
 * 
 * @Slf4j
 * 
 * @Path("/cds/properties") public class AppProperties {
 * 
 * @javax.ws.rs.core.Context ServletContext context;
 * 
 * @GET
 * 
 * @Produces("application/json") public String getAppProperties() {
 * 
 * InputStream in = AppProperties.class.getResourceAsStream(
 * "/com/sdx/platform/jsons/propsArray.json"); try { StringWriter writer = new
 * StringWriter(); IOUtils.copy(in, writer, Charset.defaultCharset()); return
 * writer.toString(); } catch (IOException e) { // TODO Auto-generated catch
 * block e.printStackTrace(); }
 * 
 * //src\main\resources\com\sdx\platform\jsons\propsArray.json String base =
 * context.getRealPath(""); System.out.println("base >>>>>>>>>>>>>> "+base);
 * File findFile = new File(String.format("%s/%s", base, ""));
 * System.out.println("Finding File "+findFile.getAbsolutePath()); try { return
 * FileUtils.readFileToString(findFile, Charset.defaultCharset()); } catch
 * (IOException e) { log.error("Error Occured", e); } return "{Error Occured}";
 * }
 * 
 * @POST
 * 
 * @Consumes("application/json") public Response createUser(User user) throws
 * URISyntaxException { if(user.getFName() == null || user.getLName() == null) {
 * return
 * Response.status(400).entity("Please provide all mandatory inputs").build(); }
 * //Mongo Insert return Response.status(201).build(); }
 * 
 * 
 * 
 * }
 */