{ pkgs ? import <nixpkgs> {} }:

with pkgs;

stdenv.mkDerivation {
  name = "baoqu-core";
  src = ./.;

  buildInputs = [
    openjdk
    leiningen
    sqlite
    rlwrap
  ];

  shellHook = ''
    alias sqlite="rlwrap sqlite3"
  '';

  inherit openjdk;
  inherit leiningen;
  builder = ./nix-package/build.sh;
}
