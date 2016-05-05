package controllers;

import play.db.jpa.Transactional;
import play.mvc.*;

import services.Loader;
import views.html.*;

import javax.inject.Inject;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {
    private final Loader loader;
    @Inject
    public HomeController(Loader loader){
        this.loader = loader;
    }

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    @Transactional(readOnly = true)
    public Result index() {
        loader.excecute();
        return ok("application is ready.");
    }

}
