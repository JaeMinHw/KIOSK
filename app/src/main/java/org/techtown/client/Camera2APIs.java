package org.techtown.client;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.util.Log;
import android.util.Size;
import android.view.Surface;

import androidx.annotation.NonNull;

import java.util.Collections;

public class Camera2APIs {
    interface Camera2Interface {
        void onCameraDeviceOpened(CameraDevice cameraDevice, Size cameraSize);
    }

    private Camera2Interface mInterface;
    private Size mCameraSize;
    private CameraCaptureSession mCaptureSession;
    private CameraDevice mCameraDevice;
    private CaptureRequest.Builder mPreviewRequestBuilder;

    public Camera2APIs(Camera2Interface impl) {
        mInterface = impl;
    }

    public CameraManager CameraManager_1(Activity activity) {
        CameraManager cameraManager = (CameraManager) activity.getSystemService(Context.CAMERA_SERVICE);
        return cameraManager;
    }

    public String CameraCharacteristics_2(CameraManager cameraManager) {
        String CI = null;
        try {
            CI = cameraManager.getCameraIdList()[4];
            CameraCharacteristics character = cameraManager.getCameraCharacteristics(cameraManager.getCameraIdList()[0]);
            StreamConfigurationMap map = character.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            for (String cameraId : cameraManager.getCameraIdList()) {
                Log.e("cameraId",cameraId);
                CameraCharacteristics characteristics = cameraManager.getCameraCharacteristics(cameraId);
                if (characteristics.get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_EXTERNAL) {
                    map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);

                    Size[] sizes = map.getOutputSizes((SurfaceTexture.class));
                    mCameraSize = sizes[0];
                    for (Size size : sizes) {
                        if (size.getWidth() > mCameraSize.getWidth()) {
                            mCameraSize = size;
                        }
                    }
                    CI = cameraId;
                }
            }
        }catch (CameraAccessException e) {
            e.printStackTrace();
        }
        return CI;
    }

    private  CameraDevice.StateCallback mCameraDevicesStateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            mCameraDevice = camera;
            mInterface.onCameraDeviceOpened(camera,mCameraSize);
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {
            camera.close();
        }

        @Override
        public void onError(@NonNull CameraDevice camera, int error) {
            camera.close();
        }
    };

    @SuppressLint("MissingPermission")
    public void CameraDevice_3(CameraManager cameraManager, String cameraId) {
        try {
            cameraManager.openCamera(cameraId, mCameraDevicesStateCallback,null);
        }catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private CameraCaptureSession.StateCallback mCaptureSessionCallback = new CameraCaptureSession.StateCallback() {
        @Override
        public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
            try {
                mCaptureSession = cameraCaptureSession;
                mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE,CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
                cameraCaptureSession.setRepeatingRequest(mPreviewRequestBuilder.build(), mCaptureCallback,null);
            }catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onConfigureFailed(@NonNull CameraCaptureSession session) {

        }
    };

    public void CaptureSession_4(CameraDevice cameraDevice, Surface surface) {
        try {
            cameraDevice.createCaptureSession(Collections.singletonList(surface),mCaptureSessionCallback,null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    public void CaptureRequest_5(CameraDevice cameraDevice, Surface surface) {
        try {
            mPreviewRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            mPreviewRequestBuilder.addTarget(surface);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private CameraCaptureSession.CaptureCallback mCaptureCallback = new CameraCaptureSession.CaptureCallback() {
        public void onCaptureProgressed(CameraCaptureSession session, CaptureRequest request, CaptureResult partialResult) {
            super.onCaptureProgressed(session,request,partialResult);
        }

        public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request, TotalCaptureResult result) {
            super.onCaptureCompleted(session,request,result);
        }
    };

    public void closeCamera() {
        if(null != mCaptureSession) {
            mCaptureSession.close();
            mCaptureSession = null;
        }

        if(null != mCameraDevice) {
            mCameraDevice.close();
            mCameraDevice = null;
        }
    }
}

