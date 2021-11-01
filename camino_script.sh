export CAMINO_HEAP_SIZE=24000

n_t=10000  # Number of time steps

for N in 0 1 2 3 4
do        
    walkers=$((10**N))
    SECONDS=0
    camino/bin/datasynth -walkers $walkers -tmax $n_t -voxels 1 -p 0.0 -schemefile results/pgse.scheme -initial file -initfile results/init_pos_$walkers.dat -substrate ply -plyfile neuron-model.ply -voxelsizefrac 1.0 > results/camino_signals_w${walkers}_t${n_t}.bfloat
    echo $SECONDS > results/camino_runtime_w${walkers}_t${n_t}.txt
done
