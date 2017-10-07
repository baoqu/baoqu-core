source $stdenv/setup

if [[ ! -f $src/target/baoqu-core-0.1-SNAPSHOT-standalone.jar ]]; then
    (>&2 echo "ERROR: Application's uberjar not found. Please run 'nix-shell --run \"lein uberjar\"' before the Nix build.")
    exit 1
fi

mkdir -p $out/lib/systemd/system
mkdir -p $out/opt/baoqu-core
mkdir -p $out/bin
cp $src/target/baoqu-core-0.1-SNAPSHOT-standalone.jar $out/opt/baoqu-core/baoqu-core.jar

sed $src/nix-package/baoqu-core.service \
    -e "s#/OUT#${out}#g" \
    -e "s#/OPENJDK#${openjdk}#g" > $out/lib/systemd/system/baoqu-core.service

sed $src/nix-package/run-baoqu-core \
    -e "s#/OUT#${out}#g" \
    -e "s#/OPENJDK#${openjdk}#g" > $out/bin/run-baoqu-core
chmod +x $out/bin/run-baoqu-core
