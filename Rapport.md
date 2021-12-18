### Questions

#### NFC

> Dans la manipulation ci-dessus, les tags NFC utilisés contiennent 4 valeurs textuelles codées en UTF-8 dans un format de message NDEF. Une personne malveillante ayant accès au porte- clés peut aisément copier les valeurs stockées dans celui-ci et les répliquer sur une autre puce NFC. 
>
> A partir de l’API Android concernant les tags NFC3, pouvez-vous imaginer une autre approche pour rendre plus compliqué le clonage des tags NFC ? Existe-il des limitations ? Voyez-vous d’autres possibilités ?

La fonction `getTechList()` permet de connaitre les technologies disponibles du tag. Certaines technologies permettent de sécuriser le tag, comme MifareClassic par exemple. Dans ce cas-là, il est possible de protéger certaines données avec un mot de passe secret. Mais cette approche requiert une connexion internet pour stocker ce mot de passe, et n'est donc pas viable sans car il faudrait le stocker en local.

Une autre possibilité est d'utiliser une clé asymétrique pour signer un challenge cryptographique, afin de vérifier si le tag est authentique à l'aide d'un challenge aléatoire. Cette manière permet de ne pas avoir à stocker de clé, ce qui la rend la plus sécurisée. 

> Est-ce qu’une solution basée sur la vérification de la présence d’un iBeacon sur l’utilisateur, par exemple sous la forme d’un porte-clés serait préférable ? Veuillez en discuter.

Non car il serait possible de cloner l'identificateur du iBeacon ce qui n'avancerait en rien le problème. De plus, les iBeacons ont une plus grande portée et le clonage est encore plus simple que pour un NFC. 



#### Code-barres

> Quelle est la quantité maximale de données pouvant être stockée sur un QR-code ? Veuillez expérimenter, avec le générateur conseillé5 de codes-barres (QR), de générer différentes tailles de QR-codes. Pensez-vous qu’il est envisageable d’utiliser confortablement des QR- codes complexes (par exemple du contenant >500 caractères de texte, une vCard très complète ou encore un certificat Covid) ?

Le générateur conseillé nous permet de créer des code QR allant de 29x29 à 547x547 symboles. Cependant, selon wikipedia et plusieurs sites, il a a 40 versions de codes QR, allant de 21x21 à 177x177, ce qui fait qu'un code QR peut contenir jusqu'à 3KB de données. 

Un texte simple peut facilement dépasser 500 caractères. Une vCard peut aussi, mais cela dépend de la taille du champ "Notes". Si l'auteur reste raisonnable, c'est totalement faisable. 



> Il existe de très nombreux services sur Internet permettant de générer des QR-codes dynamiques. Veuillez expliquer ce que sont les QR-codes dynamiques. Quels sont les avantages et respectivement les inconvénients à utiliser ceux-ci en comparaison avec des QR-codes statiques. Vous adapterez votre réponse à une utilisation depuis une plateforme mobile.

Les codes QR dynamiques sont des codes qui peuvent être modifiés même une fois imprimés. Un URL simplifié leur est donné et celui-ci redirige vers le site que nous voulons lui passer. 

Avantages :

- Pas besoin de modifier le QR pour modifier les données derrière. 
- Possibilité de traquer les informations du téléphone qui l'a scanné.
- Le code est plus court, il est donc plus rapide à scanner. 

Désavantages :

- Connexion à internet requise, tandis que les statiques peuvent être scannés sans. 

#### iBeacons

> Les iBeacons sont très souvent présentés comme une alternative à NFC. Vous commenterez cette affirmation en vous basant sur 2-3 exemples de cas d’utilisations (use-cases) concrets (par exemple e-paiement, second facteur d’identification, accéder aux horaires à un arrêt de bus, etc.).

Les iBeacons ont effectivement été développés comme une alternative à NFC. L'avantage principal est la portée du signal, qui va jusqu'à 70 mètres, contre 10 centimètres pour les NFC. Mais cela amène son lot de problèmes de sécurité. 

Cas d'utilisation :

- Pour un paiement sans contact, un NFC est bien mieux, car il demande d'être proche de la cible, ce qui est de toute manière le cas. Si un iBeacon était utilisé, il serait possible pour un attaquant d'intercepter les données bancaires.
- Pour un arrêt de bus, un iBeacon est plus adapté car la zone d'attente des passagers sera autour des 20 mètres, et aucune donnée sensible n'est transmise. Le raisonnement est le même pour n'importe quelle zone où des gens se regroupent pour attendre, tel qu'un terminal d'aéroport ou une salle de concert.
- Pareil pour un magasin, pour afficher des offres spéciales ou des nouveau produits, un iBeacon est plus adapté. 

On voit donc que les iBeacons ne sont pas totalement une alternative aux NFC, mais plutôt un complément, car les cas d'utilisations ne sont pas les mêmes. 
