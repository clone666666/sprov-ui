package xyz.sprov.blog.sprovui.route;

import spark.Request;
import spark.Route;
import xyz.sprov.blog.sprovui.bean.Msg;
import xyz.sprov.blog.sprovui.controller.InboundsController;
import xyz.sprov.blog.sprovui.util.Context;

import static spark.Spark.halt;

public class InboundsRoute {

    private InboundsController controller = Context.inboundsController;

    private Msg addOrEdit(Request request, String action) {
        Integer port = request.queryMap("port").integerValue();
        if (port == null) {
            halt(404);
        }
        String protocol = request.queryParams("protocol");
        String listen = request.queryParams("listen");
        String settings = request.queryParams("settings");
        String streamSettings = request.queryParams("streamSettings");
        String tag = request.queryParams("tag");
        if ("add".equals(action)) {
            return controller.add(listen, port, protocol, settings, streamSettings, tag);
        } else if ("edit".equals(action)) {
            return controller.edit(listen, port, protocol, settings, streamSettings, tag);
        }
        throw new IllegalArgumentException("Unknown action: " + action);
    }

    public Route add() {
        return (request, response) -> addOrEdit(request, "add");
    }

    public Route edit() {
        return (request, response) -> addOrEdit(request, "edit");
    }

    public Route del() {
        return (request, response) -> {
            Integer port = request.queryMap("port").integerValue();
            if (port == null) {
                halt(404);
            }
            return controller.del(port);
        };
    }

}
