package uk.me.jamesburt.coldstarts.cdk;

import software.amazon.awscdk.Duration;
import software.amazon.awscdk.services.lambda.*;
import software.amazon.awscdk.services.lambda.Runtime;
import software.amazon.awscdk.services.s3.Bucket;
import software.constructs.Construct;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;

public class LambdaColdStartsStack extends Stack {
    public LambdaColdStartsStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public LambdaColdStartsStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);


        Bucket.Builder.create(this, "MyFirstBucket")
                .versioned(true).build();

        final var extractFunction = Function.Builder.create(this, "BasicLambda")
                .functionName("SimpleLambda")
                .code(Code.fromAsset("./target/lambda-cold-starts-0.1.jar"))
                .handler("uk.me.jamesburt.coldstarts.lambda.SimpleLoggingLambda::handleRequest")
                .runtime(Runtime.JAVA_21)
                .timeout(Duration.minutes(2))
                .build();


        SnapStartConf conf = SnapStartConf.ON_PUBLISHED_VERSIONS;

        final var snapstartFunction = Function.Builder.create(this, "SnapstartLambda")
                .functionName("SnapstartLambda")
                .code(Code.fromAsset("./target/lambda-cold-starts-0.1.jar"))
                .handler("uk.me.jamesburt.coldstarts.lambda.SimpleLoggingLambda::handleRequest")
                .snapStart(conf)
                .runtime(Runtime.JAVA_21)
                .timeout(Duration.minutes(2))
                .build();


        final var reservedFunction = Function.Builder.create(this, "ReservedLambda")
                .functionName("ReservedLambda")
                .code(Code.fromAsset("./target/lambda-cold-starts-0.1.jar"))
                .handler("uk.me.jamesburt.coldstarts.lambda.SimpleLoggingLambda::handleRequest")
                .runtime(Runtime.JAVA_21)
                .reservedConcurrentExecutions(1)
                .timeout(Duration.minutes(2))
                .build();



        // The code that defines your stack goes here

        // example resource
        // final Queue queue = Queue.Builder.create(this, "LambdaColdStartsQueue")
        //         .visibilityTimeout(Duration.seconds(300))
        //         .build();
    }
}
