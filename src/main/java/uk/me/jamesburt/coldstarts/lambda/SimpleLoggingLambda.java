package uk.me.jamesburt.coldstarts.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class SimpleLoggingLambda implements RequestHandler<String, String> {

    private static final String FILE_NAME = "fileName";

    @Override
    public String handleRequest(String s, Context context) {
        System.out.println("Writing to the console");
        return "success";
    }

    public record Response(String message) {
    }


}
