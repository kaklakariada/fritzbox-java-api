package de.ingo.fritzbox.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * A utility class to perform GET and POST requests.
 *
 * @author Ingo Schwarz
 */
public class HttpRequest {

	private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequest.class);


	/**
	 * Performs a blocking GET request to the given URL.
	 *
	 * @param requestUrl
	 *            The URL to request.
	 * @return The result of the request as string object.
	 * @throws IOException
	 *             Exception during communication with the FritzBox.
	 */
	public static String doGet(final URL requestUrl) throws IOException {

		BufferedReader in = null;
		final StringBuilder response = new StringBuilder();
		try {
			in = new BufferedReader(new InputStreamReader(requestUrl.openConnection().getInputStream()));

			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
				response.append("\n");
			}

			return response.toString();
		} finally {
			try {
				in.close();
			} catch (final Throwable e) {
				LOGGER.debug("Exception on closing stream", e);
			}
		}
	}

	/**
	 * Performs a blocking POST request to the given URL with the given
	 * parameters.
	 *
	 * @param requestUrl
	 *            The URL to send the POST to.
	 * @param params
	 *            The parameters to post.
	 * @return The result of the request as string object.
	 * @throws IOException
	 *             Exception during communication with the FritzBox.
	 */
	public static String doPost(final URL requestUrl, final String params) throws IOException {
		final StringBuffer response = new StringBuffer();
		HttpURLConnection con = null;
		BufferedReader in = null;

		try {
			con = (HttpURLConnection) requestUrl.openConnection();

			// add reuqest header
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

			// Send post request
			con.setDoOutput(true);
			final DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(params);
			wr.flush();
			wr.close();

			in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
				response.append("\n");
			}

			return response.toString();
		} finally {
			try {
				in.close();
			} catch (final Throwable e) {
				LOGGER.debug("Exception on closing stream", e);
			}
		}
	}

}
