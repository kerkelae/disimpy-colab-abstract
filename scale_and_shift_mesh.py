import meshio
import numpy as np

mesh = meshio.read("original_mesh.ply")
shift = np.min(mesh.points, axis=0)

with open("original_mesh.ply", "r") as f:
    lines = f.readlines()

for i, line in enumerate(lines):
    if line.startswith("element vertex"):
        n_vertices = int(line.split(" ")[-1].rstrip("\n"))
    if line == "end_header\n":
        first_vertex_idx = i + 1
        break

for i in range(first_vertex_idx, first_vertex_idx + n_vertices):
    lst = lines[i].split(" ")
    for j in range(3):
        lst[j] = str((float(lst[j]) - shift[j]) * 1e-6)
    lines[i] = " ".join(lst)

with open("neuron-model.ply", "w") as f:
    lines = f.writelines(lines)
