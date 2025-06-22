from django.conf import settings
from django.http import JsonResponse
from rest_framework.decorators import api_view
from rest_framework.response import Response
import numpy as np
import onnxruntime as rt
from pathlib import Path

MODEL_PATH = Path(__file__).parent / "onnx/isoforest.onnx"
session = rt.InferenceSession(str(MODEL_PATH))
input_name = session.get_inputs()[0].name

@api_view(['POST'])
def score(request):
    try:
        data = request.data.get('payload', {})
        # This is a dummy feature extraction.
        # In a real scenario, you would extract meaningful features from the payload.
        features = np.array(list(data.values()), dtype=np.float32)
        if features.ndim == 1:
            features = features.reshape(1, -1)
        
        # Ensure the feature vector has the correct size
        if features.shape[1] != 2: # Our dummy model expects 2 features
            return Response({"error": "Invalid payload structure. Expected 2 numerical values."}, status=400)

        result = session.run(None, {input_name: features})
        score = result[1][0][0]
        label = "anomaly" if score < -0.5 else "normal"

        return Response({"score": score, "label": label})
    except Exception as e:
        return Response({"error": str(e)}, status=500)

@api_view(['GET'])
def health_check(request):
    return JsonResponse({"status": "ok"}) 