import numpy as np
from sklearn.ensemble import IsolationForest
from skl2onnx import convert_sklearn
from skl2onnx.common.data_types import FloatTensorType
import os

# Create dummy data
X = np.random.rand(100, 2)

# Train Isolation Forest model
iso_forest = IsolationForest(n_estimators=100, contamination=0.1, random_state=42)
iso_forest.fit(X)

# Convert to ONNX
initial_type = [('float_input', FloatTensorType([None, 2]))]
onnx_model = convert_sklearn(iso_forest, initial_types=initial_type, target_opset={'': 12, 'ai.onnx.ml': 3})

# Save the model
output_dir = "scoring/onnx"
os.makedirs(output_dir, exist_ok=True)
with open(os.path.join(output_dir, "isoforest.onnx"), "wb") as f:
    f.write(onnx_model.SerializeToString())

print(f"Model converted and saved to {output_dir}/isoforest.onnx") 