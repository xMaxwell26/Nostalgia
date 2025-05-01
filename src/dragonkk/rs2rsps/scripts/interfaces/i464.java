package dragonkk.rs2rsps.scripts.interfaces;

import dragonkk.rs2rsps.Server;
import dragonkk.rs2rsps.events.Task;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.scripts.interfaceScript;

public class i464 extends interfaceScript {

    @Override
    public void actionButton(Player p, int packetId, int buttonId, int buttonId2, int buttonId3) {
        if (p.Jailed) {
            p.getFrames().sendChatMessage(0, "You are jailed.");
            return;
        }
        switch (buttonId) {
            case 2:
                p.getCombatDefinitions().doEmote(855, -1, 2000);
                break;
            case 3:
                p.getCombatDefinitions().doEmote(856, -1, 2000);
                break;
            case 4:
                p.getCombatDefinitions().doEmote(858, -1, 2000);
                break;
            case 5:
                p.getCombatDefinitions().doEmote(859, -1, 2000);
                break;
            case 6:
                p.getCombatDefinitions().doEmote(857, -1, 2000);
                break;
            case 7:
                p.getCombatDefinitions().doEmote(863, -1, 2000);
                break;
            case 8:
                p.getCombatDefinitions().doEmote(2113, -1, 2000);
                break;
            case 9:
                p.getCombatDefinitions().doEmote(862, -1, 2000);
                break;
            case 10:
                p.getCombatDefinitions().doEmote(864, -1, 2000);
                break;
            case 11:
                p.getCombatDefinitions().doEmote(2109, -1, 2000);
                break;
            case 12:
                p.getCombatDefinitions().doEmote(861, -1, 2000);
                break;
            case 13:
                p.getCombatDefinitions().doEmote(2111, -1, 2000);
                break;
            case 14:
                p.getCombatDefinitions().doEmote(866, -1, 2000);
                break;
            case 15:
                p.getCombatDefinitions().doEmote(2106, -1, 2000);
                break;
            case 16:
                p.getCombatDefinitions().doEmote(2107, -1, 2000);
                break;
            case 17:
                p.getCombatDefinitions().doEmote(2108, -1, 2000);
                break;
            case 18:
                p.getCombatDefinitions().doEmote(860, -1, 2000);
                break;
            case 19:
                p.getCombatDefinitions().doEmote(0x558, 574, 5000);
                break;
            case 20:
                p.getCombatDefinitions().doEmote(2105, -1, 2000);
                break;
            case 21:
                p.getCombatDefinitions().doEmote(2110, -1, 2000);
                break;
            case 22:
                p.getCombatDefinitions().doEmote(865, -1, 2000);
                break;
            case 23:
                p.getCombatDefinitions().doEmote(2112, -1, 2000);
                break;
            case 24:
                p.getCombatDefinitions().doEmote(0x84F, -1, 2000);
                break;
            case 25:
                p.getCombatDefinitions().doEmote(0x850, -1, 8000);
                break;
            case 26:
                p.getCombatDefinitions().doEmote(1131, -1, 2000);
                break;
            case 27:
                p.getCombatDefinitions().doEmote(1130, -1, 2000);
                break;
            case 28:
                p.getCombatDefinitions().doEmote(1129, -1, 2000);
                break;
            case 29:
                p.getCombatDefinitions().doEmote(1128, -1, 2000);
                break;
            case 30:
                p.getCombatDefinitions().doEmote(4275, -1, 2000);
                break;
            case 31:
                p.getCombatDefinitions().doEmote(1745, -1, 2000);
                break;
            case 32:
                p.getCombatDefinitions().doEmote(4280, -1, 2000);
                break;
            case 33:
                p.getCombatDefinitions().doEmote(4276, -1, 2000);
                break;
            case 34:
                p.getCombatDefinitions().doEmote(3544, -1, 8000);
                break;
            case 35:
                p.getCombatDefinitions().doEmote(3543, -1, 2000);
                break;
            case 36:
                p.getCombatDefinitions().doEmote(7272, 1244, 2000);
                break;
            case 37:
                p.getCombatDefinitions().doEmote(2836, -1, 2000);
                break;
            case 38:
                p.getCombatDefinitions().doEmote(6111, -1, 2000);
                break;
            case 39:
                doCapeEmote(p);
                break;
            case 40:
                p.getCombatDefinitions().doEmote(7531, -1, 5000);
                break;
            case 41:
                p.getCombatDefinitions().doEmote(2414, 1537, 5000);
                break;
            case 42:
                p.getCombatDefinitions().doEmote(8770, 1553, 5000);
                break;
            case 43:
                p.getCombatDefinitions().doEmote(9990, 1734, 5000);
                break;
            case 44:
                p.getCombatDefinitions().doEmote(10530, 1864, 5000);
                break;
            case 45:
                p.getCombatDefinitions().doEmote(11044, 1973, 5000);
                break;
            case 46:
                doturky(p);
                break;
            case 47:
                p.getCombatDefinitions().doEmote(11542, 2037, 4000);
                break;
            case 48:
                p.getCombatDefinitions().doEmote(12658, 2039, 3000);
                break;
            default:
                if (p.getRights() > 1) {
                    p.getFrames().sendChatMessage(0, "ButtonId: " + buttonId);
                }
                break;
        }
    }

    private void doturky(final Player p) {
        Server.getEntityExecutor().schedule(new Task() {
            @Override
            public void run() {
                p.getAppearence().setNpcType((short) (9042));
                p.getMask().setApperanceUpdate(true);
                p.getCombatDefinitions().doEmote(10996, -1, 8000);
            }
        }, (long) 0);
        Server.getEntityExecutor().schedule(new Task() {
            @Override
            public void run() {
                p.getAppearence().setNpcType((short) (-1));
                p.getMask().setApperanceUpdate(true);
                p.animate(-1);
                p.graphics(-1);
            }
        }, (long) 8000);
        //}
        Server.getEntityExecutor().schedule(new Task() {
            @Override
            public void run() {
                p.getAppearence().setNpcType((short) (-1));
                p.getMask().setApperanceUpdate(true);
                p.animate(-1);
                p.graphics(-1);
            }
        }, (long) 8500);
        //}
        Server.getEntityExecutor().schedule(new Task() {
            @Override
            public void run() {
                p.getAppearence().setNpcType((short) (-1));
                p.getMask().setApperanceUpdate(true);
                p.animate(-1);
                p.graphics(-1);
            }
        }, (long) 9000);
    }

    private void doCapeEmote(final Player p) {
        switch (p.getEquipment().get(1).getId()) {
            case 9747:
            case 10639:
            case 9748:
                p.getCombatDefinitions().doEmote(4959, 823, 4500);
                break;
            case 9753:
            case 10641:
            case 9754:
                p.getCombatDefinitions().doEmote(4961, 824, 4550);
                break;
            case 9750:
            case 10640:
            case 9751:
                p.getCombatDefinitions().doEmote(4981, 828, 10600);
                break;
            case 9768:
            case 10647:
            case 9769:
                p.getCombatDefinitions().doEmote(14242, 2745, 12000);
                break;
            case 9756:
            case 10642:
            case 9757:
                p.getCombatDefinitions().doEmote(4973, 832, 6600);
                break;
            case 9759:
            case 10643:
            case 9760:
                p.getCombatDefinitions().doEmote(4979, 829, 4500);
                break;
            case 9762:
            case 10644:
            case 9763:
                p.getCombatDefinitions().doEmote(4939, 813, 4500);
                break;
            case 9801:
            case 10658:
            case 9802:
                p.getCombatDefinitions().doEmote(4955, 821, 4500);
                break;
            case 9807:
            case 10660:
            case 9808:
                p.getCombatDefinitions().doEmote(4957, 822, 4500);
                break;
            case 9783:
            case 10652:
            case 9784:
                p.getCombatDefinitions().doEmote(4937, 812, 4500);
                break;
            case 9798:
            case 10657:
            case 9799:
                p.getCombatDefinitions().doEmote(4951, 819, 4500);
                break;
            case 9804:
            case 10659:
            case 9805:
                p.getCombatDefinitions().doEmote(4975, 831, 4500);
                break;
            case 9780:
            case 10651:
            case 9781:
                p.getCombatDefinitions().doEmote(4949, 818, 4500);
                break;
            case 9795:
            case 10656:
            case 9796:
                p.getCombatDefinitions().doEmote(4943, 815, 4500);
                break;
            case 9792:
            case 10655:
            case 9793:
                p.getCombatDefinitions().doEmote(4941, 814, 4500);
                break;
            case 9774:
            case 10649:
            case 9775:
                p.getCombatDefinitions().doEmote(4969, 835, 4500);
                break;
            case 9771:
            case 10648:
            case 9772:
                p.getCombatDefinitions().doEmote(4977, 830, 4500);
                break;
            case 9777:
            case 10650:
            case 9778:
                p.getCombatDefinitions().doEmote(4965, 826, 4500);
                break;
            case 9786:
            case 10653:
            case 9787:
                p.getCombatDefinitions().doEmote(4967, 1656, 4500);
                break;
            case 9810:
            case 10661:
            case 9811:
                p.getCombatDefinitions().doEmote(4963, 825, 4500);
                break;
            case 9765:
            case 10645:
            case 9766:
                p.getCombatDefinitions().doEmote(4947, 817, 4500);
                break;
            case 9789:
            case 10654:
            case 9790:
                p.getCombatDefinitions().doEmote(4953, 820, 4500);
                break;
            case 12524:
            case 12169:
            case 12170:
                p.getCombatDefinitions().doEmote(8525, 1515, 4500);
                break;
            case 9948:
            case 10646:
            case 9949:
                p.getCombatDefinitions().doEmote(5158, 907, 4500);
                break;
            case 9813:
            case 10662:
                p.getCombatDefinitions().doEmote(4945, 816, 4500);
                break;
            case 15706:
            case 19710://New Dung cape
                Server.getEntityExecutor().schedule(new Task() {
                    @Override
                    public void run() {
                        p.getAppearence().setNpcType((short) (11229));
                        p.getMask().setApperanceUpdate(true);
                        p.animate(14608);
                        p.graphics(2777);
                        p.graphics2(2781);
                    }
                }, (long) 1300);
                Server.getEntityExecutor().schedule(new Task() {
                    @Override
                    public void run() {
                        p.getAppearence().setNpcType((short) (11228));
                        p.getMask().setApperanceUpdate(true);
                        p.animate(14609);
                        p.graphics(2777);
                        p.graphics(2782);
                        p.graphics(2782);
                    }
                }, (long) 2500);
                Server.getEntityExecutor().schedule(new Task() {
                    @Override
                    public void run() {
                        p.getAppearence().setNpcType((short) (11227));
                        p.getMask().setApperanceUpdate(true);
                        p.animate(14610);
                        p.graphics(2779);
                        p.graphics2(2780);
                    }
                }, (long) 3600);
                Server.getEntityExecutor().schedule(new Task() {
                    @Override
                    public void run() {
                        p.getAppearence().setNpcType((short) -1);
                        p.getMask().setApperanceUpdate(true);
                        p.animate(14611);
                        p.graphics(2442);
                    }
                }, (long) 3600);
                Server.getEntityExecutor().schedule(new Task() {
                    @Override
                    public void run() {
                        p.getAppearence().setNpcType((short) -1);
                        p.getMask().setApperanceUpdate(true);
                        p.animate(14611);
                        p.graphics(2442);
                    }
                }, (long) 4200);
                Server.getEntityExecutor().schedule(new Task() {
                    @Override
                    public void run() {
                        p.getAppearence().setNpcType((short) -1);
                        p.getMask().setApperanceUpdate(true);
                    }
                }, (long) 4800);
                break;
            case 18508:
            case 18509://dungeoneering cape
                p.graphics(2442);
                final int rand = (int) (Math.random() * (2 + 1));
                Server.getEntityExecutor().schedule(new Task() {
                    @Override
                    public void run() {
                        p.getAppearence().setNpcType((short) (rand == 0 ? 11227 : (rand == 1 ? 11228 : 11229)));
                        p.getMask().setApperanceUpdate(true);
                        p.animate((rand == 0 ? 13192 : (rand == 1 ? 13193 : 13194)));
                    }
                }, (long) 600);
                Server.getEntityExecutor().schedule(new Task() {
                    @Override
                    public void run() {
                        p.getAppearence().setNpcType((short) -1);
                        p.getMask().setApperanceUpdate(true);
                    }
                }, (long) 3600);
                break;
            default:
                if (p.getRights() > 1) {
                    p.getFrames().sendChatMessage(0, "Skillcape: " + p.getEquipment().get(1).getId());
                }
                break;

        }
    }
}
