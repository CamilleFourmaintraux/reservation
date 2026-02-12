# reservation
Projet démo pour un site web de réservation utilisant les technos Kotlin, SpringBoot et Graddle pour le back-end.

## Objectifs

A la manière de prendreunrendezvous, de Doctolib ou de nombreux sites de prise de rendez-vous mis en place durant la crise COVID-19, l’objectif de ce projet consiste à réaliser un site internet de gestion de rendez-vous multi-utilisateurs. Le site doit permettre d’une part de montrer aux utilisateurs les créneaux libres, d’autre part de permettre aux utilisateurs de saisir et gérer leurs rendez-vous, et évidemment de n’autoriser des rendez-vous que s’ils respectent les contraintes souhaitées pour ce site.

## Origine

Ce projet provient à la base d'un projet fait en BUT en binôme.
Les technologies back-end utilisées était Java et Maven.
J'ai donc décidé de repredre, d'adapter et d'améliorer ce projet pour utiliser les technologies demandées à Polytech.
C'est à dire refaire le projet pour utiliser Kotlin et Graddle dans le but d'apprendre efficement à les mettre en place dans un projet concret.


## Auteurs

Fourmaintraux Camille 

Mention à Noé Delin mon binôme à l'IUT avec lequel j'ai développé l'application original en binôme.


## Mise en oeuvre de l'ancien projet

Cette section décrit la mise en oeuvre de l'ancien projet, et représente les attendus pour ce nouveau projet.

### API REST

Nous avons opté pour un model API REST. Les endpoints sont les suivants :
<table>
    <caption><strong>Authentication</strong></caption>
    <tr>
        <th>Endpoints</th>
        <th>body</th>
        <th>action</th>
    </tr>
    <tr>
        <td>POST /rest/auth/signup</td>
        <td>
            <pre><code>{
    "firstname": "xxx",
    "lastname": "xxx",
    "email": "xxx",
    "address": "xxx",
    "birthday": "xxx",
    "phone": "xxx",
    "password": "xxx"
}</code></pre>
        </td>
        <td>Enregistre un nouvel utilisateur et renvoie un token.</td>
    </tr>
    <tr>
        <td>POST /rest/auth/login</td>
        <td>
            <pre><code>{
    "email": "xxx",
    "password": "xxx"
}</code></pre>
        </td>
        <td>Renvoie un token si le login est valide.</td>
    </tr>
</table>


<table>
    <caption><strong>Appuser</strong></caption>
    <tr>
        <th>Endpoints</th>
        <th>body</th>
        <th>Action</th>
    </tr>
    <tr>
        <td>GET /rest/users/me</td>
        <td></td>
        <td>Renvoie l'utilisateur courant.</td>
    </tr>
    <tr>
        <td>GET /rest/users/myappointments/{lastname}</td>
        <td></td>
        <td>Renvoie la liste des rendez-vous futur de l'utilisateur passé en paramètre.</td>
    </tr>
    <tr>
        <td>GET /rest/users/mylastappointments/{lastname}</td>
        <td></td>
        <td>Renvoie la liste des rendez-vous passés de l'utilisateur passé en paramètre.</td>
    </tr>
    <tr>
        <td>PATCH /rest/users/update/{email}</td>
        <td>
            <pre><code class="language-json">{
    "firstname": "xxx",
    "lastname": "xxx",
    "email": "xxx",
    "address": "xxx",
    "birthday": "xxx",
    "phone": "xxx",
    "password": "xxx"
}</code></pre>
        </td>
        <td>Change les données de l'utilisateur passé en paramètre. Si l'email est mis à jours un nouveau token est envoyé.</td>
    </tr>
    <tr>
        <td>POST /rest/users/picture/update/{email}?file=xxx</td>
        <td></td>
        <td>Attribut une nouvelle photo de profil à l'utilisateur passé en paramètre.</td>
    </tr>
    <tr>
        <td>GET /rest/users/picture/{email}</td>
        <td></td>
        <td>Renvoie la photo de profil de l'utilisateur passé en paramètre.</td>
    </tr>
</table>

<table>
    <caption><strong>Slots</strong></caption>
    <tr>
        <th>Endpoints</th>
        <th>action</th>
    </tr>
    <tr>
        <td>GET /rest/slots/tabDays/{monthOffset}</td>
        <td>
            Renvoie la liste des slots du mois. <code>monthOffset</code> correspond au décalage du mois demandé par rapport au mois courrant.
        </td>
    </tr>
    <tr>
        <td>DELETE /rest/slots/deleteAllSlots/{selectedDay}</td>
        <td>Supprime touts les créneaux du jour passé en paramètre.</td>
    </tr>
</table>

<table>
    <caption><strong>Appointments</strong></caption>
    <tr>
        <th>Endpoints</th>
        <th>action</th>
    </tr>
    <tr>
        <td>GET /rest/appointments/delete/{id}</td>
        <td>Renvoie le rendez-vous passé en paramètre.</td>
    </tr>
    <tr>
        <td>GET /rest/appointments/max</td>
        <td>Renvoie le nombre max de places par slot.</td>
    </tr>
    <tr>
        <td>DELETE /rest/slots/deleteSlot/{id}</td>
        <td>
            Supprime le créneaux passé en paramètre. Si le créneau a été pris en rendez-vous, ce rendez-vous est supprimé et le ou les utilisateurs sont informés par mail.
        </td>
    </tr>
    <tr>
        <td>DELETE /rest/slots/deleteAllSlots/{selectedDay}</td>
        <td>
            Supprime touts les créneaux du jour passé en paramètre. Si un ou plusieurs créneaux ont été pris en rendez-vous, ces rendez-vous sont supprimés et les utilisateurs sont informés par mail.
        </td>
    </tr>
</table>

### Autentification

L'autentification se fait donc par token. Lorsque l'utilisateur arrive sur la page `booking`, il peut visualiser les créneaux disponible, s'il en click un, il sera redirigé vers la page `login` puis reviendra sur `booking` après autentification. Pour le reste des pages comme `profile`, l'utilisateur est redirigé dés l'arrivée sur la page.

### Représentation des données


#### Schémas des données

__MCD__
![Schéma MCD](/POWERAMC/newMCD.png "MCD")

__MLD__
![Schéma MLD](/POWERAMC/newMLD.png "MLD")

Les données se présentent de la manière suivante en java :

__Les utilisateurs :__
> L'utilisateur possède une liste de rendez-vous.
```java
class AppUser {
    Long idAppUser;
    String lastname;
    String firstname;
    Date birthday;
    String address;
    String phone;
    String email;
    String profilePic;
    String password;
    Role role;
    List<Appointment> appointments;
}
```

__Les rendez-vous :__
> Un rendez-vous fait le lien entre un utilisateur et un créneau.
```java
class Appointment {
    Long idAppointment;
    AppUser appUser;
    Slot slot;
}
```

__Les créneaux :__
>  Le créneau est lié à plusieurs rendez-vous.
```java
class Slot {
    Long idSlot;
    Date dateDay;
	Time beginTime;
    Long duration;
    Long pauseDuration;
	Integer nbAppointment;
    List<Appointment> appointments;
}
```

Pour l'API REST, nous envoyont les données principalement en JSON. Le principal problème a été l'interdépendance entre `Appointment`, `AppUser` et `Slot`. Pour palier à ça, nous avons utilisé des mappers, permettant de passer d'une entité à un DTO et vice versa. la méthode principalement utilisée `.toDTO()`, possède un paramètre booleen `nested`, qui permet d'indiquer si l'entité à transformer en DTO sera un attribut d'un autre DTO. Cela permet d'ignorer certains champs qui causent un appel récursif sans fin de la méthode `.toDTO()`.


### Les propriétés modifiables

Le fichier `src/main/resources/website.properties` contiens les différents paramètres du site. Titre, jours de travail, heure de début, heure de fin, durée d'un créneau, nombre maximum d'utilisateurs par slot, message de bienvenu ...

### Calendrier

Pour la représentation du calendrier, nous avons mis un bouton vert avec le nombre de créneaux du jour. Lorsqu'une journée ne possède aucun créneau, le bouton est remplacé par un tiret. Lorsqu'une semaine déborde sur le mois vuivant ou le mois précédant, les jours en trop sont grisés. Le bouton ouvre une popup avec la liste des créneaux du jour.

Si l'utilisateur est administrateur, une petite croix apparait. Il a donc la possibilité de supprimer une journée entière de créneaux depuis la vue calendrier, ou d'en supprimer un seul depuis la liste des créneaux d'une journée.

> Sur mobile, le calendrier devient une liste de jours avec une sous liste d'heures.

### Gestion des erreurs

La classe `GlobalExceptionhandler` permet de catch la pluspart des exceptions et de renvoyer le code HTTP adéquat.

