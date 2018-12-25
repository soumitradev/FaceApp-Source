package com.facebook;

import com.facebook.internal.Logger;
import com.facebook.internal.Utility;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class GraphResponse {
    private static final String BODY_KEY = "body";
    private static final String CODE_KEY = "code";
    public static final String NON_JSON_RESPONSE_PROPERTY = "FACEBOOK_NON_JSON_RESULT";
    private static final String RESPONSE_LOG_TAG = "Response";
    public static final String SUCCESS_KEY = "success";
    private final HttpURLConnection connection;
    private final FacebookRequestError error;
    private final JSONObject graphObject;
    private final JSONArray graphObjectArray;
    private final String rawResponse;
    private final GraphRequest request;

    public enum PagingDirection {
        NEXT,
        PREVIOUS
    }

    GraphResponse(GraphRequest request, HttpURLConnection connection, String rawResponse, JSONObject graphObject) {
        this(request, connection, rawResponse, graphObject, null, null);
    }

    GraphResponse(GraphRequest request, HttpURLConnection connection, String rawResponse, JSONArray graphObjects) {
        this(request, connection, rawResponse, null, graphObjects, null);
    }

    GraphResponse(GraphRequest request, HttpURLConnection connection, FacebookRequestError error) {
        this(request, connection, null, null, null, error);
    }

    GraphResponse(GraphRequest request, HttpURLConnection connection, String rawResponse, JSONObject graphObject, JSONArray graphObjects, FacebookRequestError error) {
        this.request = request;
        this.connection = connection;
        this.rawResponse = rawResponse;
        this.graphObject = graphObject;
        this.graphObjectArray = graphObjects;
        this.error = error;
    }

    public final FacebookRequestError getError() {
        return this.error;
    }

    public final JSONObject getJSONObject() {
        return this.graphObject;
    }

    public final JSONArray getJSONArray() {
        return this.graphObjectArray;
    }

    public final HttpURLConnection getConnection() {
        return this.connection;
    }

    public GraphRequest getRequest() {
        return this.request;
    }

    public String getRawResponse() {
        return this.rawResponse;
    }

    public GraphRequest getRequestForPagedResults(PagingDirection direction) {
        String link = null;
        if (this.graphObject != null) {
            JSONObject pagingInfo = this.graphObject.optJSONObject("paging");
            if (pagingInfo != null) {
                link = direction == PagingDirection.NEXT ? pagingInfo.optString("next") : pagingInfo.optString("previous");
            }
        } else {
            GraphRequest pagingRequest = null;
        }
        if (Utility.isNullOrEmpty(link)) {
            return null;
        }
        if (link != null && link.equals(this.request.getUrlForSingleRequest())) {
            return null;
        }
        try {
            return new GraphRequest(this.request.getAccessToken(), new URL(link));
        } catch (MalformedURLException e) {
            return null;
        }
    }

    public String toString() {
        String responseCode;
        try {
            responseCode = Locale.US;
            String str = "%d";
            Object[] objArr = new Object[1];
            objArr[0] = Integer.valueOf(this.connection != null ? this.connection.getResponseCode() : 200);
            responseCode = String.format(responseCode, str, objArr);
        } catch (IOException e) {
            responseCode = "unknown";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{Response: ");
        stringBuilder.append(" responseCode: ");
        stringBuilder.append(responseCode);
        stringBuilder.append(", graphObject: ");
        stringBuilder.append(this.graphObject);
        stringBuilder.append(", error: ");
        stringBuilder.append(this.error);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    static List<GraphResponse> fromHttpConnection(HttpURLConnection connection, GraphRequestBatch requests) {
        List<GraphResponse> constructErrorResponses;
        try {
            InputStream stream;
            if (connection.getResponseCode() >= 400) {
                stream = connection.getErrorStream();
            } else {
                stream = connection.getInputStream();
            }
            List<GraphResponse> createResponsesFromStream = createResponsesFromStream(stream, connection, requests);
            Utility.closeQuietly(stream);
            return createResponsesFromStream;
        } catch (FacebookException facebookException) {
            Logger.log(LoggingBehavior.REQUESTS, RESPONSE_LOG_TAG, "Response <Error>: %s", facebookException);
            constructErrorResponses = constructErrorResponses(requests, connection, facebookException);
        } catch (Throwable exception) {
            Logger.log(LoggingBehavior.REQUESTS, RESPONSE_LOG_TAG, "Response <Error>: %s", exception);
            constructErrorResponses = constructErrorResponses(requests, connection, new FacebookException(exception));
        } catch (Throwable th) {
            Utility.closeQuietly(null);
        }
        Utility.closeQuietly(null);
        return constructErrorResponses;
    }

    static List<GraphResponse> createResponsesFromStream(InputStream stream, HttpURLConnection connection, GraphRequestBatch requests) throws FacebookException, JSONException, IOException {
        Logger.log(LoggingBehavior.INCLUDE_RAW_RESPONSES, RESPONSE_LOG_TAG, "Response (raw)\n  Size: %d\n  Response:\n%s\n", Integer.valueOf(Utility.readStreamToString(stream).length()), responseString);
        return createResponsesFromString(Utility.readStreamToString(stream), connection, requests);
    }

    static List<GraphResponse> createResponsesFromString(String responseString, HttpURLConnection connection, GraphRequestBatch requests) throws FacebookException, JSONException, IOException {
        Logger.log(LoggingBehavior.REQUESTS, RESPONSE_LOG_TAG, "Response\n  Id: %s\n  Size: %d\n  Responses:\n%s\n", requests.getId(), Integer.valueOf(responseString.length()), createResponsesFromObject(connection, requests, new JSONTokener(responseString).nextValue()));
        return createResponsesFromObject(connection, requests, new JSONTokener(responseString).nextValue());
    }

    private static List<GraphResponse> createResponsesFromObject(HttpURLConnection connection, List<GraphRequest> requests, Object object) throws FacebookException, JSONException {
        int numRequests = requests.size();
        List<GraphResponse> responses = new ArrayList(numRequests);
        Object originalResult = object;
        int i = 0;
        if (numRequests == 1) {
            GraphRequest request = (GraphRequest) requests.get(0);
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(BODY_KEY, object);
                jsonObject.put(CODE_KEY, connection != null ? connection.getResponseCode() : 200);
                JSONArray jsonArray = new JSONArray();
                jsonArray.put(jsonObject);
                JSONArray object2 = jsonArray;
            } catch (Exception e) {
                responses.add(new GraphResponse(request, connection, new FacebookRequestError(connection, e)));
            } catch (Exception e2) {
                responses.add(new GraphResponse(request, connection, new FacebookRequestError(connection, e2)));
            }
        }
        if (object2 instanceof JSONArray) {
            if (object2.length() == numRequests) {
                JSONArray jsonArray2 = object2;
                while (i < jsonArray2.length()) {
                    GraphRequest request2 = (GraphRequest) requests.get(i);
                    try {
                        responses.add(createResponseFromObject(request2, connection, jsonArray2.get(i), originalResult));
                    } catch (Exception e3) {
                        responses.add(new GraphResponse(request2, connection, new FacebookRequestError(connection, e3)));
                    } catch (Exception e32) {
                        responses.add(new GraphResponse(request2, connection, new FacebookRequestError(connection, e32)));
                    }
                    i++;
                }
                return responses;
            }
        }
        throw new FacebookException("Unexpected number of results");
    }

    private static GraphResponse createResponseFromObject(GraphRequest request, HttpURLConnection connection, Object object, Object originalResult) throws JSONException {
        if (object instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) object;
            FacebookRequestError error = FacebookRequestError.checkResponseAndCreateError(jsonObject, originalResult, connection);
            if (error != null) {
                if (error.getErrorCode() == 190 && Utility.isCurrentAccessToken(request.getAccessToken())) {
                    AccessToken.setCurrentAccessToken(null);
                }
                return new GraphResponse(request, connection, error);
            }
            Object body = Utility.getStringPropertyAsJSON(jsonObject, BODY_KEY, NON_JSON_RESPONSE_PROPERTY);
            if (body instanceof JSONObject) {
                return new GraphResponse(request, connection, body.toString(), (JSONObject) body);
            }
            if (body instanceof JSONArray) {
                return new GraphResponse(request, connection, body.toString(), (JSONArray) body);
            }
            object = JSONObject.NULL;
        }
        if (object == JSONObject.NULL) {
            return new GraphResponse(request, connection, object.toString(), (JSONObject) null);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Got unexpected object type in response, class: ");
        stringBuilder.append(object.getClass().getSimpleName());
        throw new FacebookException(stringBuilder.toString());
    }

    static List<GraphResponse> constructErrorResponses(List<GraphRequest> requests, HttpURLConnection connection, FacebookException error) {
        int count = requests.size();
        List<GraphResponse> responses = new ArrayList(count);
        for (int i = 0; i < count; i++) {
            responses.add(new GraphResponse((GraphRequest) requests.get(i), connection, new FacebookRequestError(connection, (Exception) error)));
        }
        return responses;
    }
}
