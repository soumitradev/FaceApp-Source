package com.facebook.share;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookGraphResponseException;
import com.facebook.FacebookRequestError;
import com.facebook.GraphRequest;
import com.facebook.GraphRequest.Callback;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.internal.CollectionMapper;
import com.facebook.internal.CollectionMapper.OnErrorListener;
import com.facebook.internal.CollectionMapper.OnMapValueCompleteListener;
import com.facebook.internal.CollectionMapper.OnMapperCompleteListener;
import com.facebook.internal.CollectionMapper.ValueMapper;
import com.facebook.internal.Mutable;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.Utility;
import com.facebook.share.Sharer.Result;
import com.facebook.share.internal.ShareConstants;
import com.facebook.share.internal.ShareContentValidation;
import com.facebook.share.internal.ShareInternalUtility;
import com.facebook.share.internal.VideoUploader;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.ShareOpenGraphObject;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareVideoContent;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class ShareApi {
    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final String DEFAULT_GRAPH_NODE = "me";
    private static final String GRAPH_PATH_FORMAT = "%s/%s";
    private static final String PHOTOS_EDGE = "photos";
    private static final String TAG = "ShareApi";
    private String graphNode = DEFAULT_GRAPH_NODE;
    private String message;
    private final ShareContent shareContent;

    /* renamed from: com.facebook.share.ShareApi$7 */
    class C08537 implements ValueMapper {
        C08537() {
        }

        public void mapValue(Object value, OnMapValueCompleteListener onMapValueCompleteListener) {
            if (value instanceof ArrayList) {
                ShareApi.this.stageArrayList((ArrayList) value, onMapValueCompleteListener);
            } else if (value instanceof ShareOpenGraphObject) {
                ShareApi.this.stageOpenGraphObject((ShareOpenGraphObject) value, onMapValueCompleteListener);
            } else if (value instanceof SharePhoto) {
                ShareApi.this.stagePhoto((SharePhoto) value, onMapValueCompleteListener);
            } else {
                onMapValueCompleteListener.onComplete(value);
            }
        }
    }

    public static void share(ShareContent shareContent, FacebookCallback<Result> callback) {
        new ShareApi(shareContent).share(callback);
    }

    public ShareApi(ShareContent shareContent) {
        this.shareContent = shareContent;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getGraphNode() {
        return this.graphNode;
    }

    public void setGraphNode(String graphNode) {
        this.graphNode = graphNode;
    }

    public ShareContent getShareContent() {
        return this.shareContent;
    }

    public boolean canShare() {
        if (getShareContent() == null) {
            return false;
        }
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken == null) {
            return false;
        }
        Set<String> permissions = accessToken.getPermissions();
        if (permissions == null || !permissions.contains("publish_actions")) {
            Log.w(TAG, "The publish_actions permissions are missing, the share will fail unless this app was authorized to publish in another installation.");
        }
        return true;
    }

    public void share(FacebookCallback<Result> callback) {
        if (canShare()) {
            ShareContent shareContent = getShareContent();
            try {
                ShareContentValidation.validateForApiShare(shareContent);
                if (shareContent instanceof ShareLinkContent) {
                    shareLinkContent((ShareLinkContent) shareContent, callback);
                } else if (shareContent instanceof SharePhotoContent) {
                    sharePhotoContent((SharePhotoContent) shareContent, callback);
                } else if (shareContent instanceof ShareVideoContent) {
                    shareVideoContent((ShareVideoContent) shareContent, callback);
                } else if (shareContent instanceof ShareOpenGraphContent) {
                    shareOpenGraphContent((ShareOpenGraphContent) shareContent, callback);
                }
                return;
            } catch (FacebookException ex) {
                ShareInternalUtility.invokeCallbackWithException(callback, ex);
                return;
            }
        }
        ShareInternalUtility.invokeCallbackWithError(callback, "Insufficient permissions for sharing content via Api.");
    }

    private String getGraphPath(String pathAfterGraphNode) {
        try {
            return String.format(Locale.ROOT, GRAPH_PATH_FORMAT, new Object[]{URLEncoder.encode(getGraphNode(), "UTF-8"), pathAfterGraphNode});
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    private void addCommonParameters(Bundle bundle, ShareContent shareContent) {
        Collection peopleIds = shareContent.getPeopleIds();
        if (!Utility.isNullOrEmpty(peopleIds)) {
            bundle.putString("tags", TextUtils.join(", ", peopleIds));
        }
        if (!Utility.isNullOrEmpty(shareContent.getPlaceId())) {
            bundle.putString("place", shareContent.getPlaceId());
        }
        if (!Utility.isNullOrEmpty(shareContent.getRef())) {
            bundle.putString("ref", shareContent.getRef());
        }
    }

    private void shareOpenGraphContent(ShareOpenGraphContent openGraphContent, final FacebookCallback<Result> callback) {
        final Callback requestCallback = new Callback() {
            public void onCompleted(GraphResponse response) {
                JSONObject data = response.getJSONObject();
                ShareInternalUtility.invokeCallbackWithResults(callback, data == null ? null : data.optString(ShareConstants.WEB_DIALOG_PARAM_ID), response);
            }
        };
        ShareOpenGraphAction action = openGraphContent.getAction();
        Bundle parameters = action.getBundle();
        addCommonParameters(parameters, openGraphContent);
        if (!Utility.isNullOrEmpty(getMessage())) {
            parameters.putString("message", getMessage());
        }
        final Bundle bundle = parameters;
        final ShareOpenGraphAction shareOpenGraphAction = action;
        final FacebookCallback<Result> facebookCallback = callback;
        stageOpenGraphAction(parameters, new OnMapperCompleteListener() {
            public void onComplete() {
                try {
                    ShareApi.handleImagesOnAction(bundle);
                    new GraphRequest(AccessToken.getCurrentAccessToken(), ShareApi.this.getGraphPath(URLEncoder.encode(shareOpenGraphAction.getActionType(), "UTF-8")), bundle, HttpMethod.POST, requestCallback).executeAsync();
                } catch (UnsupportedEncodingException ex) {
                    ShareInternalUtility.invokeCallbackWithException(facebookCallback, ex);
                }
            }

            public void onError(FacebookException exception) {
                ShareInternalUtility.invokeCallbackWithException(facebookCallback, exception);
            }
        });
    }

    private static void handleImagesOnAction(Bundle parameters) {
        String imageStr = parameters.getString("image");
        if (imageStr != null) {
            try {
                JSONArray images = new JSONArray(imageStr);
                for (int i = 0; i < images.length(); i++) {
                    JSONObject jsonImage = images.optJSONObject(i);
                    if (jsonImage != null) {
                        putImageInBundleWithArrayFormat(parameters, i, jsonImage);
                    } else {
                        parameters.putString(String.format(Locale.ROOT, "image[%d][url]", new Object[]{Integer.valueOf(i)}), images.getString(i));
                    }
                }
                parameters.remove("image");
            } catch (JSONException e) {
                try {
                    putImageInBundleWithArrayFormat(parameters, 0, new JSONObject(imageStr));
                    parameters.remove("image");
                } catch (JSONException e2) {
                }
            }
        }
    }

    private static void putImageInBundleWithArrayFormat(Bundle parameters, int index, JSONObject image) throws JSONException {
        Iterator<String> keys = image.keys();
        while (keys.hasNext()) {
            Object[] objArr = new Object[]{Integer.valueOf(index), (String) keys.next()};
            parameters.putString(String.format(Locale.ROOT, "image[%d][%s]", objArr), image.get((String) keys.next()).toString());
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void sharePhotoContent(com.facebook.share.model.SharePhotoContent r27, com.facebook.FacebookCallback<com.facebook.share.Sharer.Result> r28) {
        /*
        r26 = this;
        r7 = r26;
        r8 = r28;
        r1 = new com.facebook.internal.Mutable;
        r2 = 0;
        r2 = java.lang.Integer.valueOf(r2);
        r1.<init>(r2);
        r9 = r1;
        r22 = com.facebook.AccessToken.getCurrentAccessToken();
        r1 = new java.util.ArrayList;
        r1.<init>();
        r13 = r1;
        r3 = new java.util.ArrayList;
        r3.<init>();
        r4 = new java.util.ArrayList;
        r4.<init>();
        r15 = new com.facebook.share.ShareApi$3;
        r1 = r15;
        r2 = r7;
        r5 = r9;
        r6 = r8;
        r1.<init>(r3, r4, r5, r6);
        r1 = r27.getPhotos();	 Catch:{ FileNotFoundException -> 0x00d7 }
        r1 = r1.iterator();	 Catch:{ FileNotFoundException -> 0x00d7 }
    L_0x0034:
        r2 = r1.hasNext();	 Catch:{ FileNotFoundException -> 0x00d7 }
        if (r2 == 0) goto L_0x00a9;
    L_0x003a:
        r2 = r1.next();	 Catch:{ FileNotFoundException -> 0x00d7 }
        r2 = (com.facebook.share.model.SharePhoto) r2;	 Catch:{ FileNotFoundException -> 0x00d7 }
        r5 = 0;
        r6 = r27;
        r14 = r7.getSharePhotoCommonParameters(r2, r6);	 Catch:{ JSONException -> 0x00a0 }
        r5 = r2.getBitmap();	 Catch:{ FileNotFoundException -> 0x009e }
        r10 = r2.getImageUrl();	 Catch:{ FileNotFoundException -> 0x009e }
        r23 = r10;
        r10 = r2.getCaption();	 Catch:{ FileNotFoundException -> 0x009e }
        if (r10 != 0) goto L_0x0063;
    L_0x0058:
        r11 = r26.getMessage();	 Catch:{ FileNotFoundException -> 0x005e }
        r10 = r11;
        goto L_0x0063;
    L_0x005e:
        r0 = move-exception;
        r2 = r0;
        r1 = r13;
        goto L_0x00dc;
    L_0x0063:
        r24 = r10;
        if (r5 == 0) goto L_0x007d;
    L_0x0067:
        r10 = "photos";
        r11 = r7.getGraphPath(r10);	 Catch:{ FileNotFoundException -> 0x009e }
        r10 = r22;
        r12 = r5;
        r25 = r1;
        r1 = r13;
        r13 = r24;
        r10 = com.facebook.GraphRequest.newUploadPhotoRequest(r10, r11, r12, r13, r14, r15);	 Catch:{ FileNotFoundException -> 0x00d4 }
        r1.add(r10);	 Catch:{ FileNotFoundException -> 0x00d4 }
        goto L_0x0099;
    L_0x007d:
        r25 = r1;
        r1 = r13;
        if (r23 == 0) goto L_0x0099;
    L_0x0082:
        r10 = "photos";
        r17 = r7.getGraphPath(r10);	 Catch:{ FileNotFoundException -> 0x00d4 }
        r16 = r22;
        r18 = r23;
        r19 = r24;
        r20 = r14;
        r21 = r15;
        r10 = com.facebook.GraphRequest.newUploadPhotoRequest(r16, r17, r18, r19, r20, r21);	 Catch:{ FileNotFoundException -> 0x00d4 }
        r1.add(r10);	 Catch:{ FileNotFoundException -> 0x00d4 }
        r13 = r1;
        r1 = r25;
        goto L_0x0034;
    L_0x009e:
        r0 = move-exception;
        goto L_0x00da;
    L_0x00a0:
        r0 = move-exception;
        r25 = r1;
        r1 = r13;
        r10 = r0;
        com.facebook.share.internal.ShareInternalUtility.invokeCallbackWithException(r8, r10);	 Catch:{ FileNotFoundException -> 0x00d4 }
        return;
    L_0x00a9:
        r6 = r27;
        r1 = r13;
        r2 = r9.value;	 Catch:{ FileNotFoundException -> 0x00d4 }
        r2 = (java.lang.Integer) r2;	 Catch:{ FileNotFoundException -> 0x00d4 }
        r2 = r2.intValue();	 Catch:{ FileNotFoundException -> 0x00d4 }
        r5 = r1.size();	 Catch:{ FileNotFoundException -> 0x00d4 }
        r2 = r2 + r5;
        r2 = java.lang.Integer.valueOf(r2);	 Catch:{ FileNotFoundException -> 0x00d4 }
        r9.value = r2;	 Catch:{ FileNotFoundException -> 0x00d4 }
        r2 = r1.iterator();	 Catch:{ FileNotFoundException -> 0x00d4 }
    L_0x00c3:
        r5 = r2.hasNext();	 Catch:{ FileNotFoundException -> 0x00d4 }
        if (r5 == 0) goto L_0x00d3;
    L_0x00c9:
        r5 = r2.next();	 Catch:{ FileNotFoundException -> 0x00d4 }
        r5 = (com.facebook.GraphRequest) r5;	 Catch:{ FileNotFoundException -> 0x00d4 }
        r5.executeAsync();	 Catch:{ FileNotFoundException -> 0x00d4 }
        goto L_0x00c3;
    L_0x00d3:
        goto L_0x00df;
    L_0x00d4:
        r0 = move-exception;
        r2 = r0;
        goto L_0x00dc;
    L_0x00d7:
        r0 = move-exception;
        r6 = r27;
    L_0x00da:
        r1 = r13;
        r2 = r0;
    L_0x00dc:
        com.facebook.share.internal.ShareInternalUtility.invokeCallbackWithException(r8, r2);
    L_0x00df:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.share.ShareApi.sharePhotoContent(com.facebook.share.model.SharePhotoContent, com.facebook.FacebookCallback):void");
    }

    private void shareLinkContent(ShareLinkContent linkContent, final FacebookCallback<Result> callback) {
        Callback requestCallback = new Callback() {
            public void onCompleted(GraphResponse response) {
                JSONObject data = response.getJSONObject();
                ShareInternalUtility.invokeCallbackWithResults(callback, data == null ? null : data.optString(ShareConstants.WEB_DIALOG_PARAM_ID), response);
            }
        };
        Bundle parameters = new Bundle();
        addCommonParameters(parameters, linkContent);
        parameters.putString("message", getMessage());
        parameters.putString("link", Utility.getUriString(linkContent.getContentUrl()));
        parameters.putString("picture", Utility.getUriString(linkContent.getImageUrl()));
        parameters.putString("name", linkContent.getContentTitle());
        parameters.putString("description", linkContent.getContentDescription());
        parameters.putString("ref", linkContent.getRef());
        new GraphRequest(AccessToken.getCurrentAccessToken(), getGraphPath("feed"), parameters, HttpMethod.POST, requestCallback).executeAsync();
    }

    private void shareVideoContent(ShareVideoContent videoContent, FacebookCallback<Result> callback) {
        try {
            VideoUploader.uploadAsync(videoContent, getGraphNode(), callback);
        } catch (FileNotFoundException ex) {
            ShareInternalUtility.invokeCallbackWithException(callback, ex);
        }
    }

    private Bundle getSharePhotoCommonParameters(SharePhoto photo, SharePhotoContent photoContent) throws JSONException {
        Bundle params = photo.getParameters();
        if (!(params.containsKey("place") || Utility.isNullOrEmpty(photoContent.getPlaceId()))) {
            params.putString("place", photoContent.getPlaceId());
        }
        if (!(params.containsKey("tags") || Utility.isNullOrEmpty(photoContent.getPeopleIds()))) {
            Collection<String> peopleIds = photoContent.getPeopleIds();
            if (!Utility.isNullOrEmpty((Collection) peopleIds)) {
                JSONArray tags = new JSONArray();
                for (String id : peopleIds) {
                    JSONObject tag = new JSONObject();
                    tag.put("tag_uid", id);
                    tags.put(tag);
                }
                params.putString("tags", tags.toString());
            }
        }
        if (!(params.containsKey("ref") || Utility.isNullOrEmpty(photoContent.getRef()))) {
            params.putString("ref", photoContent.getRef());
        }
        return params;
    }

    private void stageArrayList(final ArrayList arrayList, final OnMapValueCompleteListener onArrayListStagedListener) {
        final JSONArray stagedObject = new JSONArray();
        stageCollectionValues(new CollectionMapper.Collection<Integer>() {
            public Iterator<Integer> keyIterator() {
                final int size = arrayList.size();
                final Mutable<Integer> current = new Mutable(Integer.valueOf(0));
                return new Iterator<Integer>() {
                    public boolean hasNext() {
                        return ((Integer) current.value).intValue() < size;
                    }

                    public Integer next() {
                        Integer num = (Integer) current.value;
                        Mutable mutable = current;
                        mutable.value = Integer.valueOf(((Integer) mutable.value).intValue() + 1);
                        return num;
                    }

                    public void remove() {
                    }
                };
            }

            public Object get(Integer key) {
                return arrayList.get(key.intValue());
            }

            public void set(Integer key, Object value, OnErrorListener onErrorListener) {
                try {
                    stagedObject.put(key.intValue(), value);
                } catch (JSONException ex) {
                    String message = ex.getLocalizedMessage();
                    if (message == null) {
                        message = "Error staging object.";
                    }
                    onErrorListener.onError(new FacebookException(message));
                }
            }
        }, new OnMapperCompleteListener() {
            public void onComplete() {
                onArrayListStagedListener.onComplete(stagedObject);
            }

            public void onError(FacebookException exception) {
                onArrayListStagedListener.onError(exception);
            }
        });
    }

    private <T> void stageCollectionValues(CollectionMapper.Collection<T> collection, OnMapperCompleteListener onCollectionValuesStagedListener) {
        CollectionMapper.iterate(collection, new C08537(), onCollectionValuesStagedListener);
    }

    private void stageOpenGraphAction(final Bundle parameters, OnMapperCompleteListener onOpenGraphActionStagedListener) {
        stageCollectionValues(new CollectionMapper.Collection<String>() {
            public Iterator<String> keyIterator() {
                return parameters.keySet().iterator();
            }

            public Object get(String key) {
                return parameters.get(key);
            }

            public void set(String key, Object value, OnErrorListener onErrorListener) {
                if (!Utility.putJSONValueInBundle(parameters, key, value)) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unexpected value: ");
                    stringBuilder.append(value.toString());
                    onErrorListener.onError(new FacebookException(stringBuilder.toString()));
                }
            }
        }, onOpenGraphActionStagedListener);
    }

    private void stageOpenGraphObject(final ShareOpenGraphObject object, final OnMapValueCompleteListener onOpenGraphObjectStagedListener) {
        String type = object.getString("type");
        if (type == null) {
            type = object.getString("og:type");
        }
        if (type == null) {
            onOpenGraphObjectStagedListener.onError(new FacebookException("Open Graph objects must contain a type value."));
            return;
        }
        final JSONObject stagedObject = new JSONObject();
        C08559 collection = new CollectionMapper.Collection<String>() {
            public Iterator<String> keyIterator() {
                return object.keySet().iterator();
            }

            public Object get(String key) {
                return object.get(key);
            }

            public void set(String key, Object value, OnErrorListener onErrorListener) {
                try {
                    stagedObject.put(key, value);
                } catch (JSONException ex) {
                    String message = ex.getLocalizedMessage();
                    if (message == null) {
                        message = "Error staging object.";
                    }
                    onErrorListener.onError(new FacebookException(message));
                }
            }
        };
        final Callback requestCallback = new Callback() {
            public void onCompleted(GraphResponse response) {
                FacebookRequestError error = response.getError();
                if (error != null) {
                    String message = error.getErrorMessage();
                    if (message == null) {
                        message = "Error staging Open Graph object.";
                    }
                    onOpenGraphObjectStagedListener.onError(new FacebookGraphResponseException(response, message));
                    return;
                }
                JSONObject data = response.getJSONObject();
                if (data == null) {
                    onOpenGraphObjectStagedListener.onError(new FacebookGraphResponseException(response, "Error staging Open Graph object."));
                    return;
                }
                String stagedObjectId = data.optString(ShareConstants.WEB_DIALOG_PARAM_ID);
                if (stagedObjectId == null) {
                    onOpenGraphObjectStagedListener.onError(new FacebookGraphResponseException(response, "Error staging Open Graph object."));
                } else {
                    onOpenGraphObjectStagedListener.onComplete(stagedObjectId);
                }
            }
        };
        final String ogType = type;
        final JSONObject jSONObject = stagedObject;
        final OnMapValueCompleteListener onMapValueCompleteListener = onOpenGraphObjectStagedListener;
        stageCollectionValues(collection, new OnMapperCompleteListener() {
            public void onComplete() {
                String objectString = jSONObject.toString();
                Bundle parameters = new Bundle();
                parameters.putString("object", objectString);
                try {
                    AccessToken currentAccessToken = AccessToken.getCurrentAccessToken();
                    ShareApi shareApi = ShareApi.this;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("objects/");
                    stringBuilder.append(URLEncoder.encode(ogType, "UTF-8"));
                    new GraphRequest(currentAccessToken, shareApi.getGraphPath(stringBuilder.toString()), parameters, HttpMethod.POST, requestCallback).executeAsync();
                } catch (UnsupportedEncodingException ex) {
                    String message = ex.getLocalizedMessage();
                    if (message == null) {
                        message = "Error staging Open Graph object.";
                    }
                    onMapValueCompleteListener.onError(new FacebookException(message));
                }
            }

            public void onError(FacebookException exception) {
                onMapValueCompleteListener.onError(exception);
            }
        });
    }

    private void stagePhoto(final SharePhoto photo, final OnMapValueCompleteListener onPhotoStagedListener) {
        Bitmap bitmap = photo.getBitmap();
        Uri imageUrl = photo.getImageUrl();
        if (bitmap == null) {
            if (imageUrl == null) {
                onPhotoStagedListener.onError(new FacebookException("Photos must have an imageURL or bitmap."));
                return;
            }
        }
        Callback requestCallback = new Callback() {
            public void onCompleted(GraphResponse response) {
                FacebookRequestError error = response.getError();
                if (error != null) {
                    String message = error.getErrorMessage();
                    if (message == null) {
                        message = "Error staging photo.";
                    }
                    onPhotoStagedListener.onError(new FacebookGraphResponseException(response, message));
                    return;
                }
                JSONObject data = response.getJSONObject();
                if (data == null) {
                    onPhotoStagedListener.onError(new FacebookException("Error staging photo."));
                    return;
                }
                String stagedImageUri = data.optString(ShareConstants.MEDIA_URI);
                if (stagedImageUri == null) {
                    onPhotoStagedListener.onError(new FacebookException("Error staging photo."));
                    return;
                }
                JSONObject stagedObject = new JSONObject();
                try {
                    stagedObject.put("url", stagedImageUri);
                    stagedObject.put(NativeProtocol.IMAGE_USER_GENERATED_KEY, photo.getUserGenerated());
                    onPhotoStagedListener.onComplete(stagedObject);
                } catch (JSONException ex) {
                    String message2 = ex.getLocalizedMessage();
                    if (message2 == null) {
                        message2 = "Error staging photo.";
                    }
                    onPhotoStagedListener.onError(new FacebookException(message2));
                }
            }
        };
        if (bitmap != null) {
            ShareInternalUtility.newUploadStagingResourceWithImageRequest(AccessToken.getCurrentAccessToken(), bitmap, requestCallback).executeAsync();
        } else {
            try {
                ShareInternalUtility.newUploadStagingResourceWithImageRequest(AccessToken.getCurrentAccessToken(), imageUrl, requestCallback).executeAsync();
            } catch (FileNotFoundException ex) {
                String message = ex.getLocalizedMessage();
                if (message == null) {
                    message = "Error staging photo.";
                }
                onPhotoStagedListener.onError(new FacebookException(message));
            }
        }
    }
}
