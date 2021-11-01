import numpy as np
import matplotlib.pyplot as plt

plt.rcParams["font.family"] = "serif"

n_walkers = np.array([10 ** i for i in range(7)])
disimpy_runtimes = np.zeros(7)
n_camino_measurements = 5
camino_runtimes = np.zeros(n_camino_measurements)

for i in range(7):
    N = 10 ** i
    disimpy_runtimes[i] = np.loadtxt(f"results/disimpy_runtime_w{N}_t10000.txt")
    if i < n_camino_measurements:
        camino_runtimes[i] = np.loadtxt(f"results/camino_runtime_w{N}_t10000.txt")

p = np.poly1d(np.polyfit(n_walkers[0:n_camino_measurements], camino_runtimes, 1))

fig, ax = plt.subplots(1, figsize=(7, 4))
ax.scatter(n_walkers, disimpy_runtimes, color="tab:green", marker="o")
ax.scatter(
    n_walkers[0:n_camino_measurements], camino_runtimes, color="tab:blue", marker="o"
)
ax.scatter(
    n_walkers[n_camino_measurements::],
    p(n_walkers[n_camino_measurements::]),
    color="tab:blue",
    marker="s",
)
ax.legend(["Disimpy", "Camino", "Camino (extrapolated)"], borderpad=0.75)
ax.set_xlabel("Number of random walkers")
ax.set_ylabel("Runtime (s)")
ax.set_xscale("log")
ax.set_yscale("log")
ax.grid()
ax.set_axisbelow(True)
fig.tight_layout()
plt.show()

fig.savefig("figures/runtime_comparison.png", dpi=600)

print(
    max(
        p(n_walkers[n_camino_measurements::])
        / disimpy_runtimes[n_camino_measurements::]
    )
)
