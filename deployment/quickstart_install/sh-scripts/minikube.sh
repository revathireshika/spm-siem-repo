#!/bin/sh

profile=siembol

echo == install prerequisites ==
brew install helm kubernetes-cli minikube mkcert nss wget

echo == create and install CA ==
mkcert -install

echo == create k8s cluster ==
minikube start --profile $profile --driver virtualbox --cpus 6 --memory 10g --disk-size 40g --addons ingress,ingress-dns
minikube profile $profile

echo == install cert-manager ==
helm repo add jetstack https://charts.jetstack.io
helm repo update
kubectl create namespace cert-manager
helm install cert-manager jetstack/cert-manager --namespace cert-manager --set installCRDs=true


printf '== wait for cert-manager to be up'
for deployment in cert-manager cert-manager-cainjector cert-manager-webhook
do
  while ! [ "$(kubectl get deployment $deployment --namespace cert-manager -o jsonpath='{.status.readyReplicas}')" == "1" ]; do printf .; sleep 1; done
done
echo ' =='


echo == install CA in siembol namespace ==
kubectl create namespace $profile
kubectl create -n $profile secret tls cacerts --cert="$(mkcert -CAROOT)/rootCA.pem" --key="$(mkcert -CAROOT)/rootCA-key.pem"
kubectl apply -f - <<EOF
apiVersion: cert-manager.io/v1
kind: Issuer
metadata:
  name: ca-issuer
  namespace: $profile
spec:
  ca:
    secretName: cacerts
EOF
