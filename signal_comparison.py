import numpy as np
import matplotlib.pyplot as plt

plt.rcParams["font.family"] = "serif"

n_walkers = int(1e4)
bs = np.linspace(1, 5e9, 11) / 1e9
camino_signals = np.fromfile(
    f"results/camino_signals_w{n_walkers}_t10000.bfloat", ">f4"
)
disimpy_signals = np.loadtxt(f"results/disimpy_signals_w{n_walkers}_t10000.txt")

fig, ax = plt.subplots(1, figsize=(7, 4))
ax.scatter(bs, disimpy_signals / n_walkers, color="tab:green")
ax.scatter(bs, camino_signals / n_walkers, color="tab:blue", marker="x", s=100)
ax.set_ylabel("S/S$_0$")
ax.set_xlabel("b (ms/μm$^2$)")
ax.set_xticks(bs)
ax.legend(["Disimpy", "Camino"])
ax.grid()
ax.set_axisbelow(True)
fig.tight_layout()
plt.show()

fig.savefig("figures/signal_comparison.png", dpi=600)
