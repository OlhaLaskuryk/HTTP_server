package command;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

public class CommandHTTPUtil {
	private static Map<String, ICommandHTTP> commands = null;

	public static ICommandHTTP getCommand(String command) {
		if (commands == null) {
			initCommands();
		}
		return commands.get(command);
	}

	private static void initCommands() {
		commands = new HashMap<String, ICommandHTTP>();
		commands.put("count", new ICommandHTTP() {
			@Override
			public String execute(String args) {
				StringBuilder answer = new StringBuilder();
				if (!args.isEmpty()) {
					answer.append("HTTP/1.1 400 Bad Request\r\n");
					answer.append("\r\n");
					return answer.toString();
				}
				// Integer count = instanceDAO.getGoodDAO().getCount();
				Integer count = 903;
				JSONObject json = new JSONObject();
				json.put(new String("count"), count);
				answer = new StringBuilder();
				answer.append("HTTP/1.1 200 OK\r\n");
				answer.append("\r\n");
				answer.append(json + "\r\n");
				return answer.toString();
			}

		});
		commands.put("item", new ICommandHTTP() {

			@Override
			public String execute(String args) {
				StringBuilder answer = new StringBuilder();
				if (args.isEmpty() || !args.startsWith("?")) {
					answer.append("HTTP/1.1 400 Bad Request\r\n");
					answer.append("\r\n");
					return answer.toString();
				}
				String parameters = args.substring(1);
				String[] argsSplit = parameters.split("&");
				if (argsSplit.length > 1) {
					answer.append("HTTP/1.1 400 Bad Request\r\n");
					answer.append("\r\n");
					return answer.toString();
				}
				String[] argsPair = argsSplit[0].split("=");
				Map<String, String> map = new HashMap<String, String>();
				try {
					map.put(argsPair[0], argsPair[1]);
				} catch (ArrayIndexOutOfBoundsException e) {
					answer.append("HTTP/1.1 400 Bad Request\r\n");
					answer.append("\r\n");
					return answer.toString();
				}catch(Exception e){
					answer.append("HTTP/1.1 404 Bad Request\r\n");
					answer.append("\r\n");
					return answer.toString();
				}
				try {
					JSONObject json = new JSONObject();
					Integer index = Integer.parseInt(map.get("get_info"));
					// Stationery o =
					// instanceDAO.getGoodDAO().getGoodById(index);
					Object o = null;
					if (index > 0 && index % 2 == 0) {
						o = new String();
						json.put(new String("name"), "Look");
						json.put(new String("price"), 56.90);
					}
					answer.append("HTTP/1.1 200 OK\r\n");
					answer.append("\r\n");
					if (o != null) {
						// bw.write(o.getBrand() + "|" +
						// o.getPrice());

						answer.append(json + "\r\n");
						return answer.toString();
					} else {
						answer.append("Not exist" + "\r\n");
						return answer.toString();
					}
				} catch (NumberFormatException e) {
					answer.append("HTTP/1.1 400 Bad Request\r\n");
					answer.append("\r\n");
					answer.append("Incorrect data" + "\r\n");
					return answer.toString();
				}
			}
		});
	}
}
