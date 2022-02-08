# Hospital Medical Tracking Project

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white) ![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white) ![MicrosoftSQLServer](https://img.shields.io/badge/Microsoft%20SQL%20Sever-CC2927?style=for-the-badge&logo=microsoft%20sql%20server&logoColor=white) ![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white)

## Meet Development Team

### Lead Software Developer ([Amitai Erfanian](https://github.com/Amitai5))
The member with strongest technical background of the team, often with past work
experience, and has good social/team skills. This position should be determined first. This person will coordinate the assistant lead software engineers, lead the training of other team members, lead the software design meetings, and perform code reviews. This position comes with great responsibility but also with great rewards in the future. The lead software engineer is a key player in maximizing the productivity of each person on the team, and the team as a whole. Making this decision correctly is vital to the success of your project!

### Assistant Software Engineers ([Joe Dobbelaar](https://github.com/r2pen2) and [Samay Govani](https://github.com/samaygo89))
The members with the next strongest technical background of the team. These two members will work with the lead software engineer in helping to coordinate the other software engineers, particularly subgroups of developers, and perform code reviews.

### Full-Time Software Engineers
These positions are usually, but not always, filled by your CS majors. Initially you will need
1. Software Feature Engineers: user interface and controllers ([Camilo Cordoba Escobar](https://github.com/ccordoba464) and [Haohao Yi](https://github.com/HaohaoYi))
2. Back-End Database Engineers: Apache Derby SQL DB, ORM/DAO/Repository ([Brinda Venkataraman](https://github.com/brindavenk8) and [Jose Morales](https://github.com/JoseMorales7))
3. Algorithm specialists ([Maanav Iyengar](https://github.com/miyengar26) and [Jeremy Trembley](https://github.com/jtrembleywpi))

### Project Manager ([Maanav Iyengar](https://github.com/xxmiap))
This member will ideally have had experience with managing a multiple person project(s) in the past
and has enough technical background to be involved in and understand the design of the application and how it is being implemented. This position is ideal for someone who is interested in learning how to manage software engineers. This person will be responsible for team management communications with the VP and help keep track of team tasks. It is very important that the team leaders understand that the most important task of a project manager is to help create and foster a collegial, professional environment in which the team and all its members can operate efficiently, effectively, productively, and with mutual respect. Please note that this person still has to contribute Java code to the project, however, at a reduced level taking into account the other responsibilities. I, as the VP, will provide project management advice and training as needed.

### Co-Scrum Masters ([Maanav Iyengar](https://github.com/xxmiap))
The scrum master will run the daily scrums, sprint planning meetings, sprint review meetings, and sprint reflection meetings. The scrum master will also train the rest of the team on how the sprints are executed, particularly the various meetings. I will provide information on how to run Scrums in class but team coaches will work with the scrum masters to ensure they perform their duties properly. Note that there will be other types of meetings that will be led by other members. For example, the project manager will lead longer meetings concerning task tracking, product owners will lead the meetings on requirements gathering and user stories, and the lead software engineer will lead meetings concerning design, refactoring, and implementation.

### Product Owner ([Camilo Cordoba Escobar](https://github.com/ccordoba464))
This position is responsible for coordinating and tracking the prioritized list of software requirements and user stories. The person in this position will also serve initially as one of the Software Feature Engineers concentrating on user interfaces.

### Documentation Analyst [Jeremy Trembley](https://github.com/jtrembleywpi))
One software engineer will also be responsible for coordinating the artifacts created by the project: project documentation, requirements documentation, models, user documentation and training materials, and software code documentation. This person is responsible for maintaining well organized documents so that the VP or the client can inspect them immediately upon request. A suitable person for this position would be someone who is meticulous and well organized. Normally this position would be filled by a team member who is NOT a computer science major and is interested in contributing significantly through non-programming tasks. An exception would be a computer science major who has a strong interest in becoming a technical writer instead of a software engineer. The document analyst does not, however, have the sole responsibility for creating documentation; this responsibility is shared equally by all team members, particularly in the case of source code documentation. Please note that the documentation analyst still has to contribute to the project by writing Java code.

## Project Introduction

The professor will work as the Vice President of Software Engineering directing overall strategy – determining overarching goals and planning to achieve those goals – and managing the teams by assigning students to the teams, training of positions, and monitoring and evaluating each team’s work. The teams themselves are in charge of tactical execution – the creation of the application by gathering requirements, conducting analysis, formulating designs, writing code, and testing. Team coaches will assist with technical logistics by confirming that teams have necessary software resources, helping teams overcome technical obstacles, and guiding teams to success.

Over the course of this term, your team will create a desktop application for employees and administrators at Mass General Brigham Hospital in Boston. The base requirements (not a complete set) for the desktop application are

1. Medical equipment tracking and workflow service that includes the following, mostly around the 3rd floor inpatient unit:
	1. Beds are in the patient rooms, parked in the hallways, or stored in the OR Bed Park on Lower Level 1. Beds in the hallways may either be waiting to be put into the patient room, have a patient in it awaiting transportation to another location, or are “dirty” and waiting to be delivered to the OR Bed Park for cleaning and storage.
	2. There is 1 portable X-Ray that is stored near the unit or in use in one of the patient rooms
	3. Infusion Pumps are stored clean on the unit, attached to IV poles near the patient in the room, placed in a dirty pick-up area outside the unit, brought to the West Plaza for cleaning, and then returned to the clean storage area on the unit
	4. There are 6 patient recliners that go into some rooms during the day and the Bed comes out of the room and is parked in the hallway. If there are an excess of patient recliners in the hallway they can go to the West Plaza on the 1st floor.

2. A graphical map editing tool to handle floor maps, paths, locations, and display of medical equipment on the floor map. The addition of a vertical (side-view) hospital map that displays the location and count of medical equipment for all of the floors in the hospital tower at a glance.

3. A collection of additional service request components that permit service requests to be made for a given location. Each team member, except for those responsible for the medical equipment tracking/workflow service, needs to create their own component and associated controller. For the less experienced programmers concerned about their grades, doing a good job on your service components and controllers goes a long way to ensuring a good grade in the class! Some possibilities are listed:
	1. Food delivery service
	2. Language interpreters
	3. Sanitation services – cleaning up spills, rooms, and public spaces
	4. Laundry services
	5. Gift delivery service for presents purchased at the hospital
	6. Floral delivery service
	7. Medicine delivery service
	8. Religious requests such as blessings or last rites. If you implement this component, be aware that multiple religions need to be taken into account.
	9. Internal patient transportation (transportation for a patient inside the hospital)
	10. External patient transportation (ambulance, helicopter, etc) for a patient to be transported to a location outside of the hospital
	11. Security services
	12. Facilities maintenance requests including elevator and power issues
	13. Computer service requests
	14. Audio/visual requests
	15. Laundry services

4. A professional quality, consistent user interface across all application components. Use of Material Design (JFoenix) is one possibility but not required. Alternative approaches are also acceptable.

5. Different methods of data storage –embedded database, client-server, and NoSQL cloud storage with CSV files as backup.

6. A medical equipment movement simulator. Create a simulation where medical equipment is requested by health professionals and the equipment eventually winds up in the “dirty” areas. This generates a medical equipment transportation request to get the equipment back to “clean” storage areas.

7. (Strictly Optional) Graphical pathfinding. This is a major feature to implement and so do not do this unless you are confident you will be successful. Users can select a starting and an ending location and the application application will visually display the path to their destinations along with text directions on how to get there. This is used in conjunction with the service request components for delivery of equipment, patients, food, gifts or other services.
